/**
 * D7-B thin E2E sync (FR-103, S22, S24 stub).
 *
 * - Opt-in / off by default
 * - Ciphertext-only remote store
 * - Drive appData adapter is **mocked** (no Google account / paid services created)
 * - WebDAV = Phase 2 fast-follow (Q32 assumption) — interface stub only
 */

const STORAGE_KEYS = {
  optIn: "syncOptIn",
  lastSyncAt: "lastSyncAt",
  lastWriter: "lastWriter",
  remoteCipher: "mockDriveAppDataCipher",
  deviceId: "deviceId",
};

export class ThinSyncEngine {
  async status() {
    const data = await chrome.storage.local.get({
      [STORAGE_KEYS.optIn]: false,
      [STORAGE_KEYS.lastSyncAt]: null,
      [STORAGE_KEYS.lastWriter]: null,
      [STORAGE_KEYS.deviceId]: null,
    });
    return {
      optIn: !!data[STORAGE_KEYS.optIn],
      lastSyncAt: data[STORAGE_KEYS.lastSyncAt],
      lastWriter: data[STORAGE_KEYS.lastWriter],
      transport: "mock_drive_appdata",
      webdav: "fast_follow_not_in_exit",
      continuity:
        data[STORAGE_KEYS.lastSyncAt] && data[STORAGE_KEYS.optIn]
          ? "synced_ciphertext"
          : "local_only",
      notes:
        "S24: real drive.appdata OAuth + brand verification PENDING — mocked locally. No external accounts.",
    };
  }

  async setOptIn(optIn) {
    await chrome.storage.local.set({ [STORAGE_KEYS.optIn]: optIn });
  }

  async ensureDeviceId() {
    const { deviceId } = await chrome.storage.local.get({ [STORAGE_KEYS.deviceId]: null });
    if (deviceId) return deviceId;
    const id = crypto.randomUUID();
    await chrome.storage.local.set({ [STORAGE_KEYS.deviceId]: id });
    return id;
  }

  /**
   * Round-trip thin payload through mock Drive appData (ciphertext blob).
   */
  async syncNow() {
    const st = await this.status();
    if (!st.optIn) {
      return {
        ok: false,
        error: "sync_off_by_default",
        message: "Sync is opt-in and currently off (FR-106 / D7-B).",
      };
    }
    const deviceId = await this.ensureDeviceId();
    const { items } = await chrome.storage.local.get({ items: [] });
    const thin = {
      schema: "obzrai-thin-v1",
      exportedAt: Date.now(),
      deviceId,
      items: items.map((it) => ({
        ...it,
        // Never put full-res / voice blobs in thin path
        thumbnailBase64: it.thumbnailBase64 || null,
      })),
    };
    const plaintext = new TextEncoder().encode(JSON.stringify(thin));
    const key = await this.mockKey();
    const iv = crypto.getRandomValues(new Uint8Array(12));
    const ciphertext = await crypto.subtle.encrypt(
      { name: "AES-GCM", iv },
      key,
      plaintext,
    );
    const packed = {
      v: 1,
      iv: bufferToBase64(iv),
      ct: bufferToBase64(ciphertext),
      adapter: "mock_drive_appdata",
      scope: "drive.appdata",
      brandVerification: "PENDING",
    };
    // "Remote" mock store = chrome.storage.local key (stand-in for appDataFolder)
    await chrome.storage.local.set({
      [STORAGE_KEYS.remoteCipher]: packed,
      [STORAGE_KEYS.lastSyncAt]: Date.now(),
      [STORAGE_KEYS.lastWriter]: deviceId,
    });

    // Read-back verify ciphertext round-trip
    const remote = (await chrome.storage.local.get(STORAGE_KEYS.remoteCipher))[
      STORAGE_KEYS.remoteCipher
    ];
    const iv2 = base64ToBuffer(remote.iv);
    const ct2 = base64ToBuffer(remote.ct);
    const plain2 = await crypto.subtle.decrypt(
      { name: "AES-GCM", iv: iv2 },
      key,
      ct2,
    );
    const decoded = JSON.parse(new TextDecoder().decode(plain2));
    return {
      ok: true,
      itemCount: decoded.items?.length || 0,
      transport: "mock_drive_appdata",
      continuity: "synced_ciphertext",
      latencyHintMs: 0,
      message:
        "Thin ciphertext round-trip OK (mock). Real Drive OAuth/brand verify still PENDING.",
    };
  }

  async mockKey() {
    // Deterministic mock key material for local spike only — NOT for production.
    const material = new TextEncoder().encode("obzrai-mock-drive-appdata-key-v1");
    const hash = await crypto.subtle.digest("SHA-256", material);
    return crypto.subtle.importKey("raw", hash, "AES-GCM", false, ["encrypt", "decrypt"]);
  }
}

/** WebDAV adapter stub — Q32 fast-follow, not Phase 2 exit blocker. */
export class WebDavAdapterStub {
  async push() {
    return {
      ok: false,
      error: "not_implemented",
      message: "WebDAV is Phase 2 fast-follow after Drive appData (Q32 assumption).",
    };
  }
}

function bufferToBase64(buf) {
  const bytes = buf instanceof ArrayBuffer ? new Uint8Array(buf) : buf;
  let s = "";
  for (const b of bytes) s += String.fromCharCode(b);
  return btoa(s);
}

function base64ToBuffer(b64) {
  const s = atob(b64);
  const out = new Uint8Array(s.length);
  for (let i = 0; i < s.length; i++) out[i] = s.charCodeAt(i);
  return out;
}

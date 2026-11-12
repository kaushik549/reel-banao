const multer = require('multer');
const path = require('path');
const fs = require('fs');

const UPLOAD_ROOT = path.join(__dirname, '..', '..', 'uploads');

// Ensure upload subdirectories exist
['videos', 'audio', 'images', 'exports'].forEach(subDir => {
  const dirPath = path.join(UPLOAD_ROOT, subDir);
  if (!fs.existsSync(dirPath)) {
    fs.mkdirSync(dirPath, { recursive: true });
  }
});

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    let folder = 'images';
    if (file.mimetype.startsWith('video/')) {
      folder = 'videos';
    } else if (file.mimetype.startsWith('audio/')) {
      folder = 'audio';
    }
    cb(null, path.join(UPLOAD_ROOT, folder));
  },
  filename: (req, file, cb) => {
    const ext = path.extname(file.originalname);
    const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1e9);
    cb(null, `${file.fieldname}-${uniqueSuffix}${ext}`);
  }
});

const fileFilter = (req, file, cb) => {
  const allowed = [
    'video/mp4', 'video/quicktime', 'video/webm', 'video/3gpp',
    'image/jpeg', 'image/png', 'image/webp',
    'audio/mpeg', 'audio/mp3', 'audio/wav', 'audio/aac', 'audio/ogg'
  ];

  if (allowed.includes(file.mimetype)) {
    cb(null, true);
  } else {
    cb(new Error(`अमान्य फ़ाइल प्रकार: ${file.mimetype} समर्थित नहीं है।`), false);
  }
};

const maxMb = parseInt(process.env.MAX_FILE_SIZE_MB || '100', 10);

const upload = multer({
  storage,
  fileFilter,
  limits: { fileSize: maxMb * 1024 * 1024 }
});

module.exports = upload;


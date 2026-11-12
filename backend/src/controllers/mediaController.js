const path = require('path');

exports.uploadSingle = (req, res, next) => {
  try {
    if (!req.file) {
      return res.status(400).json({
        success: false,
        message: 'कोई फ़ाइल अपलोड नहीं की गई (No file uploaded)'
      });
    }

    const baseUrl = process.env.BASE_URL || `http://localhost:${process.env.PORT || 5000}`;
    const subFolder = req.file.mimetype.startsWith('video/') ? 'videos' : req.file.mimetype.startsWith('audio/') ? 'audio' : 'images';
    const fileUrl = `${baseUrl}/uploads/${subFolder}/${req.file.filename}`;

    res.status(201).json({
      success: true,
      message: 'फ़ाइल सफलतापूर्वक अपलोड हो गई (File uploaded successfully)',
      data: {
        filename: req.file.filename,
        originalName: req.file.originalname,
        mimetype: req.file.mimetype,
        sizeBytes: req.file.size,
        url: fileUrl
      }
    });
  } catch (err) {
    next(err);
  }
};

exports.uploadMultiple = (req, res, next) => {
  try {
    if (!req.files || req.files.length === 0) {
      return res.status(400).json({
        success: false,
        message: 'कोई फ़ाइलें नहीं मिलीं (No files uploaded)'
      });
    }

    const baseUrl = process.env.BASE_URL || `http://localhost:${process.env.PORT || 5000}`;
    const uploaded = req.files.map(file => {
      const subFolder = file.mimetype.startsWith('video/') ? 'videos' : file.mimetype.startsWith('audio/') ? 'audio' : 'images';
      return {
        filename: file.filename,
        originalName: file.originalname,
        mimetype: file.mimetype,
        sizeBytes: file.size,
        url: `${baseUrl}/uploads/${subFolder}/${file.filename}`
      };
    });

    res.status(201).json({
      success: true,
      count: uploaded.length,
      data: uploaded
    });
  } catch (err) {
    next(err);
  }
};


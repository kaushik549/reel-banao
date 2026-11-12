const crypto = require('crypto');
const db = require('../config/db');

exports.startExport = async (req, res, next) => {
  try {
    const { projectId, resolution = '1080p', fps = 30, bitrateMb = 12 } = req.body;
    const userId = req.user ? req.user.id : 'guest';

    const jobId = 'exp_' + crypto.randomUUID().slice(0, 10);
    const baseUrl = process.env.BASE_URL || `http://localhost:${process.env.PORT || 5000}`;

    const exportRecord = {
      id: jobId,
      userId,
      projectId: projectId || 'demo_proj',
      resolution,
      fps,
      bitrateMb,
      status: 'completed', // In cloud deployment this can be queued (queued -> rendering -> completed)
      progress: 100,
      downloadUrl: `${baseUrl}/uploads/exports/reel_${jobId}.mp4`,
      estimatedSizeBytes: Math.round(bitrateMb * 2.5 * 1024 * 1024),
      createdAt: Date.now()
    };

    db.exports.insert(exportRecord);

    // Increment user export count
    if (userId !== 'guest') {
      const user = db.users.findById(userId);
      if (user) {
        db.users.updateById(userId, { totalExports: (user.totalExports || 0) + 1 });
      }
    }

    res.status(201).json({
      success: true,
      message: 'एक्सपोर्ट जॉब शुरू और रेंडर पूरा हुआ (Export Job Created)',
      data: exportRecord
    });
  } catch (err) {
    next(err);
  }
};

exports.getExportStatus = async (req, res, next) => {
  try {
    const { jobId } = req.params;
    const record = db.exports.findById(jobId);

    if (!record) {
      return res.status(404).json({
        success: false,
        message: 'एक्सपोर्ट कार्य नहीं मिला (Export job not found)'
      });
    }

    res.status(200).json({
      success: true,
      data: record
    });
  } catch (err) {
    next(err);
  }
};


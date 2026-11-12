const db = require('../config/db');

exports.getTracks = async (req, res, next) => {
  try {
    const { category, search } = req.query;

    let tracks = db.music.find();

    if (category && category !== 'All') {
      tracks = tracks.filter(t => t.category.toLowerCase() === category.toLowerCase());
    }

    if (search) {
      const q = search.toLowerCase();
      tracks = tracks.filter(t =>
        t.title.toLowerCase().includes(q) ||
        t.artist.toLowerCase().includes(q)
      );
    }

    res.status(200).json({
      success: true,
      count: tracks.length,
      data: tracks
    });
  } catch (err) {
    next(err);
  }
};

exports.getTrackById = async (req, res, next) => {
  try {
    const { id } = req.params;
    const track = db.music.findById(id);

    if (!track) {
      return res.status(404).json({
        success: false,
        message: 'ऑडियो ट्रैक नहीं मिला (Track not found)'
      });
    }

    res.status(200).json({
      success: true,
      data: track
    });
  } catch (err) {
    next(err);
  }
};


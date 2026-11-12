const aiService = require('../services/aiService');
const db = require('../config/db');
const crypto = require('crypto');

exports.getAiStyles = (req, res) => {
  const styles = [
    {
      id: 'ai_beat_punch',
      name: 'Beat-Punch High Energy',
      description: 'हर म्यूजिक बीट पर फास्ट कट्स, फ्लैश और 3D ज़ूम इफेक्ट्स',
      suggestedBpm: 128
    },
    {
      id: 'ai_cinematic_travel',
      name: 'Cinematic Storyteller',
      description: 'स्मूथ स्पीड-रैंपिंग, 24fps फिल्म लुक और ऑटोमैटिक कलर ग्रेडिंग',
      suggestedBpm: 90
    },
    {
      id: 'ai_retro_nostalgia',
      name: 'Retro VHS Memories',
      description: 'विंटेज वॉर्म टोन, लाइट लीक्स और 90s डेट स्टैम्प',
      suggestedBpm: 85
    },
    {
      id: 'ai_smart_slideshow',
      name: 'Smart Face-Sync Slideshow',
      description: 'स्मार्ट फेस डिटेक्शन और बीट सिंक फोटो ट्रांजीशन',
      suggestedBpm: 110
    }
  ];

  res.status(200).json({ success: true, count: styles.length, data: styles });
};

exports.autoEdit = async (req, res, next) => {
  try {
    const { mediaUrls = [], styleId = 'ai_beat_punch', mood = 'Trending Beats', isBeatSync = true } = req.body;

    if (!mediaUrls || mediaUrls.length === 0) {
      return res.status(400).json({
        success: false,
        message: 'कृपया कम से कम 1 फोटो या वीडियो क्लिप प्रदान करें (Media URLs required)'
      });
    }

    const aiResult = await aiService.processAutoEdit({
      mediaUrls,
      styleId,
      mood,
      isBeatSync
    });

    // Optionally auto-create a project if user is logged in
    let savedProject = null;
    if (req.user) {
      savedProject = {
        id: 'proj_' + crypto.randomUUID().slice(0, 8),
        userId: req.user.id,
        title: `AI Auto Reel (${mood})`,
        coverUri: mediaUrls[0],
        durationMs: aiResult.totalDurationMs,
        aspectRatio: '9:16',
        resolution: '1080p',
        fps: 30,
        clips: aiResult.clips,
        createdAt: Date.now(),
        updatedAt: Date.now()
      };
      db.projects.insert(savedProject);
    }

    res.status(200).json({
      success: true,
      message: 'AI वीडियो सफलतापूर्वक जनरेट हुआ (AI Video Generated Successfully)',
      data: {
        aiMetadata: aiResult,
        project: savedProject
      }
    });
  } catch (err) {
    next(err);
  }
};


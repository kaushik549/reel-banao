const express = require('express');
const router = express.Router();
const aiController = require('../controllers/aiController');
const { optionalAuth } = require('../middleware/auth');

router.get('/styles', aiController.getAiStyles);
router.post('/auto-edit', optionalAuth, aiController.autoEdit);

module.exports = router;


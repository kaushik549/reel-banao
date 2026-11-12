const express = require('express');
const router = express.Router();
const upload = require('../middleware/upload');
const mediaController = require('../controllers/mediaController');
const { optionalAuth } = require('../middleware/auth');

router.post('/upload', optionalAuth, upload.single('media'), mediaController.uploadSingle);
router.post('/upload-multiple', optionalAuth, upload.array('media', 15), mediaController.uploadMultiple);

module.exports = router;


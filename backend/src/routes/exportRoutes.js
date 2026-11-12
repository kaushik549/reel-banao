const express = require('express');
const router = express.Router();
const exportController = require('../controllers/exportController');
const { optionalAuth } = require('../middleware/auth');

router.post('/start', optionalAuth, exportController.startExport);
router.get('/status/:jobId', exportController.getExportStatus);

module.exports = router;


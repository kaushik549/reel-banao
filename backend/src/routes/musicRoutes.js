const express = require('express');
const router = express.Router();
const musicController = require('../controllers/musicController');

router.get('/', musicController.getTracks);
router.get('/:id', musicController.getTrackById);

module.exports = router;


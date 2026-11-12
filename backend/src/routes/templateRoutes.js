const express = require('express');
const router = express.Router();
const templateController = require('../controllers/templateController');

router.get('/categories', templateController.getCategories);
router.get('/', templateController.getTemplates);
router.get('/:id', templateController.getTemplateById);
router.post('/:id/like', templateController.toggleLike);

module.exports = router;


const express = require('express');
const router = express.Router();
const projectController = require('../controllers/projectController');
const { requireAuth } = require('../middleware/auth');

router.use(requireAuth);

router.get('/', projectController.getUserProjects);
router.post('/', projectController.createProject);
router.get('/:id', projectController.getProjectById);
router.put('/:id', projectController.updateProject);
router.post('/:id/duplicate', projectController.duplicateProject);
router.delete('/:id', projectController.deleteProject);

module.exports = router;


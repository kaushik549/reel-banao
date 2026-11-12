const express = require('express');
const { body } = require('express-validator');
const router = express.Router();
const authController = require('../controllers/authController');
const { requireAuth } = require('../middleware/auth');
const validate = require('../middleware/validator');

router.post(
  '/register',
  [
    body('name').trim().notEmpty().withMessage('नाम आवश्यक है (Name is required)'),
    body('email').isEmail().withMessage('मान्य ईमेल दर्ज करें (Valid email required)'),
    body('password').isLength({ min: 6 }).withMessage('पासवर्ड कम से कम 6 अक्षरों का होना चाहिए')
  ],
  validate,
  authController.register
);

router.post(
  '/login',
  [
    body('email').isEmail().withMessage('मान्य ईमेल दर्ज करें'),
    body('password').notEmpty().withMessage('पासवर्ड आवश्यक है')
  ],
  validate,
  authController.login
);

router.post('/guest', authController.guestLogin);

router.get('/profile', requireAuth, authController.getProfile);

module.exports = router;


const { validationResult } = require('express-validator');

const validate = (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'इनपुट सत्यापन विफल (Input Validation Failed)',
      errors: errors.array().map(err => ({ field: err.path, message: err.msg }))
    });
  }
  next();
};

module.exports = validate;


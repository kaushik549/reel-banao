const jwt = require('jsonwebtoken');
const db = require('../config/db');

const JWT_SECRET = process.env.JWT_SECRET || 'reelbanao_secret_key_default';

/**
 * Require valid JWT Token
 */
const requireAuth = (req, res, next) => {
  const authHeader = req.headers.authorization;
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({
      success: false,
      message: 'अनधिकृत प्रवेश: कृपया लॉगिन टोकन प्रदान करें (Authentication token required)'
    });
  }

  const token = authHeader.split(' ')[1];
  try {
    const decoded = jwt.verify(token, JWT_SECRET);
    req.user = decoded;
    next();
  } catch (err) {
    return res.status(401).json({
      success: false,
      message: 'टोकन अमान्य या समाप्त हो गया है (Invalid or expired token)'
    });
  }
};

/**
 * Optional Auth (For public or guest browsing)
 */
const optionalAuth = (req, res, next) => {
  const authHeader = req.headers.authorization;
  if (authHeader && authHeader.startsWith('Bearer ')) {
    const token = authHeader.split(' ')[1];
    try {
      req.user = jwt.verify(token, JWT_SECRET);
    } catch {
      // Ignore invalid token in optional mode
    }
  }
  next();
};

module.exports = {
  requireAuth,
  optionalAuth
};


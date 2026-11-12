const errorHandler = (err, req, res, next) => {
  console.error('[Error Details]:', err.stack || err.message);

  const statusCode = err.statusCode || (res.statusCode !== 200 ? res.statusCode : 500);

  res.status(statusCode).json({
    success: false,
    message: err.message || 'सर्वर में आंतरिक त्रुटि हुई (Internal Server Error)',
    stack: process.env.NODE_ENV === 'development' ? err.stack : undefined
  });
};

module.exports = errorHandler;


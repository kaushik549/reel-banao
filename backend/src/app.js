const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const morgan = require('morgan');
const path = require('path');

const authRoutes = require('./routes/authRoutes');
const templateRoutes = require('./routes/templateRoutes');
const projectRoutes = require('./routes/projectRoutes');
const mediaRoutes = require('./routes/mediaRoutes');
const musicRoutes = require('./routes/musicRoutes');
const aiRoutes = require('./routes/aiRoutes');
const exportRoutes = require('./routes/exportRoutes');
const errorHandler = require('./middleware/errorHandler');

const app = express();

// Security & Logging
app.use(helmet({ crossOriginResourcePolicy: false }));
app.use(cors());
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: true, limit: '50mb' }));

if (process.env.NODE_ENV !== 'test') {
  app.use(morgan('dev'));
}

// Serve uploaded files (Videos, Images, Audio, Rendered Reels)
app.use('/uploads', express.static(path.join(__dirname, '..', 'uploads')));

// Health check endpoint
app.get('/api/health', (req, res) => {
  res.status(200).json({
    status: 'healthy',
    app: 'Reel Banao REST API',
    version: '1.0.0',
    timestamp: new Date().toISOString()
  });
});

// API Routes (v1)
app.use('/api/v1/auth', authRoutes);
app.use('/api/v1/templates', templateRoutes);
app.use('/api/v1/projects', projectRoutes);
app.use('/api/v1/media', mediaRoutes);
app.use('/api/v1/music', musicRoutes);
app.use('/api/v1/ai', aiRoutes);
app.use('/api/v1/export', exportRoutes);

// 404 Handler
app.use((req, res) => {
  res.status(404).json({
    success: false,
    message: `रूट नहीं मिला (Route not found): ${req.method} ${req.originalUrl}`
  });
});

// Centralized Error Handler
app.use(errorHandler);

module.exports = app;


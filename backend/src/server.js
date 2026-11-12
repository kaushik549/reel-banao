require('dotenv').config();
const app = require('./app');
const seedData = require('./data/seed');

const PORT = process.env.PORT || 5000;

// Ensure database seed data exists on first boot
seedData();

app.listen(PORT, () => {
  console.log(`====================================================`);
  console.log(`🚀 Reel Banao Backend API Server Running on Port ${PORT}`);
  console.log(`🌐 Base URL: http://localhost:${PORT}`);
  console.log(`📡 Health Check: http://localhost:${PORT}/api/health`);
  console.log(`====================================================`);
});


const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const crypto = require('crypto');
const db = require('../config/db');

const JWT_SECRET = process.env.JWT_SECRET || 'reelbanao_secret_key_default';
const JWT_EXPIRES_IN = process.env.JWT_EXPIRES_IN || '30d';

const generateToken = (user) => {
  return jwt.sign(
    { id: user.id, email: user.email, isGuest: user.isGuest || false },
    JWT_SECRET,
    { expiresIn: JWT_EXPIRES_IN }
  );
};

exports.register = async (req, res, next) => {
  try {
    const { name, email, password } = req.body;

    const existing = db.users.findOne(u => u.email === email.toLowerCase());
    if (existing) {
      return res.status(400).json({
        success: false,
        message: 'यह ईमेल पहले से पंजीकृत है (Email already registered)'
      });
    }

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    const newUser = {
      id: 'usr_' + crypto.randomUUID().slice(0, 8),
      name,
      username: '@' + name.toLowerCase().replace(/\s+/g, '_') + '_' + Math.floor(Math.random() * 900 + 100),
      email: email.toLowerCase(),
      password: hashedPassword,
      avatarUrl: null,
      isVip: false,
      isGuest: false,
      totalProjects: 0,
      totalExports: 0,
      storageUsedMb: 0,
      maxStorageMb: 2048,
      createdAt: Date.now()
    };

    db.users.insert(newUser);

    const { password: _, ...userSafe } = newUser;
    const token = generateToken(newUser);

    res.status(201).json({
      success: true,
      message: 'अकाउंट सफलतापूर्वक बनाया गया (Account created successfully)',
      data: { user: userSafe, token }
    });
  } catch (err) {
    next(err);
  }
};

exports.login = async (req, res, next) => {
  try {
    const { email, password } = req.body;

    const user = db.users.findOne(u => u.email === email.toLowerCase());
    if (!user) {
      return res.status(401).json({
        success: false,
        message: 'ईमेल या पासवर्ड गलत है (Invalid email or password)'
      });
    }

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return res.status(401).json({
        success: false,
        message: 'ईमेल या पासवर्ड गलत है (Invalid email or password)'
      });
    }

    const { password: _, ...userSafe } = user;
    const token = generateToken(user);

    res.status(200).json({
      success: true,
      message: 'सफलतापूर्वक लॉगिन हुआ (Login successful)',
      data: { user: userSafe, token }
    });
  } catch (err) {
    next(err);
  }
};

exports.guestLogin = async (req, res, next) => {
  try {
    const guestId = 'guest_' + crypto.randomUUID().slice(0, 8);
    const guestUser = {
      id: guestId,
      name: 'Guest Creator',
      username: '@guest_' + Math.floor(Math.random() * 9000 + 1000),
      email: `guest_${guestId}@reelbanao.local`,
      isVip: false,
      isGuest: true,
      totalProjects: 0,
      totalExports: 0,
      storageUsedMb: 10,
      maxStorageMb: 1024,
      createdAt: Date.now()
    };

    db.users.insert(guestUser);
    const token = generateToken(guestUser);

    res.status(200).json({
      success: true,
      message: 'गेस्ट मोड सक्रिय (Guest mode activated)',
      data: { user: guestUser, token }
    });
  } catch (err) {
    next(err);
  }
};

exports.getProfile = async (req, res, next) => {
  try {
    const user = db.users.findById(req.user.id);
    if (!user) {
      return res.status(404).json({ success: false, message: 'यूज़र नहीं मिला (User not found)' });
    }

    const { password: _, ...userSafe } = user;
    res.status(200).json({
      success: true,
      data: userSafe
    });
  } catch (err) {
    next(err);
  }
};


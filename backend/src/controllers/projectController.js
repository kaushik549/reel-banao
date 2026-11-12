const crypto = require('crypto');
const db = require('../config/db');

exports.getUserProjects = async (req, res, next) => {
  try {
    const userId = req.user.id;
    const projects = db.projects.find(p => p.userId === userId);
    res.status(200).json({
      success: true,
      count: projects.length,
      data: projects
    });
  } catch (err) {
    next(err);
  }
};

exports.createProject = async (req, res, next) => {
  try {
    const userId = req.user.id;
    const {
      title = 'Untitled Reel',
      aspectRatio = '9:16',
      resolution = '1080p',
      fps = 30,
      clips = [],
      audioTracks = [],
      textLayers = []
    } = req.body;

    const newProject = {
      id: 'proj_' + crypto.randomUUID().slice(0, 8),
      userId,
      title,
      coverUri: clips[0]?.uriString || null,
      durationMs: clips.reduce((acc, c) => acc + (c.totalDurationMs || 0), 0),
      aspectRatio,
      resolution,
      fps,
      clips,
      audioTracks,
      textLayers,
      createdAt: Date.now(),
      updatedAt: Date.now()
    };

    db.projects.insert(newProject);

    // Update user project count
    const user = db.users.findById(userId);
    if (user) {
      db.users.updateById(userId, { totalProjects: (user.totalProjects || 0) + 1 });
    }

    res.status(201).json({
      success: true,
      message: 'प्रोजेक्ट सफलतापूर्वक बनाया गया (Project created)',
      data: newProject
    });
  } catch (err) {
    next(err);
  }
};

exports.getProjectById = async (req, res, next) => {
  try {
    const { id } = req.params;
    const project = db.projects.findById(id);

    if (!project) {
      return res.status(404).json({
        success: false,
        message: 'प्रोजेक्ट नहीं मिला (Project not found)'
      });
    }

    res.status(200).json({
      success: true,
      data: project
    });
  } catch (err) {
    next(err);
  }
};

exports.updateProject = async (req, res, next) => {
  try {
    const { id } = req.params;
    const project = db.projects.findById(id);

    if (!project) {
      return res.status(404).json({
        success: false,
        message: 'प्रोजेक्ट नहीं मिला (Project not found)'
      });
    }

    const updated = db.projects.updateById(id, req.body);

    res.status(200).json({
      success: true,
      message: 'प्रोजेक्ट अपडेट हो गया (Project updated)',
      data: updated
    });
  } catch (err) {
    next(err);
  }
};

exports.duplicateProject = async (req, res, next) => {
  try {
    const { id } = req.params;
    const original = db.projects.findById(id);

    if (!original) {
      return res.status(404).json({
        success: false,
        message: 'मूल प्रोजेक्ट नहीं मिला (Original project not found)'
      });
    }

    const duplicate = {
      ...original,
      id: 'proj_' + crypto.randomUUID().slice(0, 8),
      title: `${original.title} (Copy)`,
      createdAt: Date.now(),
      updatedAt: Date.now()
    };

    db.projects.insert(duplicate);

    res.status(201).json({
      success: true,
      message: 'प्रोजेक्ट कॉपी हो गया (Project duplicated)',
      data: duplicate
    });
  } catch (err) {
    next(err);
  }
};

exports.deleteProject = async (req, res, next) => {
  try {
    const { id } = req.params;
    const success = db.projects.deleteById(id);

    if (!success) {
      return res.status(404).json({
        success: false,
        message: 'प्रोजेक्ट नहीं मिला या पहले ही डिलीट हो चुका है'
      });
    }

    res.status(200).json({
      success: true,
      message: 'प्रोजेक्ट डिलीट हो गया (Project deleted)'
    });
  } catch (err) {
    next(err);
  }
};


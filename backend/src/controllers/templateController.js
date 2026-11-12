const db = require('../config/db');

exports.getCategories = async (req, res, next) => {
  try {
    const categories = db.categories.find();
    res.status(200).json({
      success: true,
      count: categories.length,
      data: categories
    });
  } catch (err) {
    next(err);
  }
};

exports.getTemplates = async (req, res, next) => {
  try {
    const {
      page = 1,
      limit = 20,
      search = '',
      categoryId,
      tab = 'all' // 'all', 'trending', 'latest', 'popular', 'favorites'
    } = req.query;

    const pageNum = parseInt(page, 10) || 1;
    const limitNum = parseInt(limit, 10) || 20;

    let filtered = db.templates.find(template => {
      // Search filter
      const matchesSearch = !search ||
        template.title.toLowerCase().includes(search.toLowerCase()) ||
        template.categoryName.toLowerCase().includes(search.toLowerCase()) ||
        (template.tags && template.tags.some(t => t.toLowerCase().includes(search.toLowerCase())));

      // Category filter
      const matchesCategory = !categoryId || categoryId === 'all' || template.categoryId === categoryId;

      // Tab filter
      let matchesTab = true;
      if (tab === 'trending') matchesTab = template.isTrending === true;
      else if (tab === 'latest') matchesTab = template.isLatest === true;
      else if (tab === 'popular') matchesTab = template.isPopular === true;
      else if (tab === 'favorites') matchesTab = template.isFavorite === true;

      return matchesSearch && matchesCategory && matchesTab;
    });

    const totalCount = filtered.length;
    const totalPages = Math.ceil(totalCount / limitNum);
    const startIndex = (pageNum - 1) * limitNum;
    const paginatedData = filtered.slice(startIndex, startIndex + limitNum);

    res.status(200).json({
      success: true,
      meta: {
        totalTemplates: totalCount,
        totalPages,
        currentPage: pageNum,
        limit: limitNum,
        hasMore: pageNum < totalPages
      },
      data: paginatedData
    });
  } catch (err) {
    next(err);
  }
};

exports.getTemplateById = async (req, res, next) => {
  try {
    const { id } = req.params;
    const template = db.templates.findById(id);

    if (!template) {
      return res.status(404).json({
        success: false,
        message: 'टेम्प्लेट नहीं मिला (Template not found)'
      });
    }

    res.status(200).json({
      success: true,
      data: template
    });
  } catch (err) {
    next(err);
  }
};

exports.toggleLike = async (req, res, next) => {
  try {
    const { id } = req.params;
    const template = db.templates.findById(id);

    if (!template) {
      return res.status(404).json({
        success: false,
        message: 'टेम्प्लेट नहीं मिला (Template not found)'
      });
    }

    const updated = db.templates.updateById(id, {
      isFavorite: !template.isFavorite,
      likesCount: template.isFavorite ? template.likesCount - 1 : template.likesCount + 1
    });

    res.status(200).json({
      success: true,
      message: updated.isFavorite ? 'पसंदीदा में जोड़ा गया' : 'पसंदीदा से हटाया गया',
      data: updated
    });
  } catch (err) {
    next(err);
  }
};


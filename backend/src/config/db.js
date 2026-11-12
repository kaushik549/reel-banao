const fs = require('fs');
const path = require('path');

const DATA_DIR = path.join(__dirname, '..', '..', 'data_storage');

if (!fs.existsSync(DATA_DIR)) {
  fs.mkdirSync(DATA_DIR, { recursive: true });
}

class Collection {
  constructor(name) {
    this.name = name;
    this.filePath = path.join(DATA_DIR, `${name}.json`);
    this.data = this._load();
  }

  _load() {
    try {
      if (fs.existsSync(this.filePath)) {
        const raw = fs.readFileSync(this.filePath, 'utf-8');
        return JSON.parse(raw || '[]');
      }
    } catch (err) {
      console.error(`Error loading collection ${this.name}:`, err.message);
    }
    return [];
  }

  _save() {
    try {
      fs.writeFileSync(this.filePath, JSON.stringify(this.data, null, 2), 'utf-8');
    } catch (err) {
      console.error(`Error saving collection ${this.name}:`, err.message);
    }
  }

  find(predicate = () => true) {
    return this.data.filter(predicate);
  }

  findOne(predicate) {
    return this.data.find(predicate) || null;
  }

  findById(id) {
    return this.data.find(item => item.id === id) || null;
  }

  insert(record) {
    this.data.unshift(record);
    this._save();
    return record;
  }

  insertMany(records) {
    this.data.push(...records);
    this._save();
    return records;
  }

  updateById(id, updates) {
    const index = this.data.findIndex(item => item.id === id);
    if (index === -1) return null;
    this.data[index] = { ...this.data[index], ...updates, updatedAt: Date.now() };
    this._save();
    return this.data[index];
  }

  deleteById(id) {
    const index = this.data.findIndex(item => item.id === id);
    if (index === -1) return false;
    this.data.splice(index, 1);
    this._save();
    return true;
  }

  count(predicate = () => true) {
    return this.data.filter(predicate).length;
  }
}

const db = {
  users: new Collection('users'),
  templates: new Collection('templates'),
  categories: new Collection('categories'),
  projects: new Collection('projects'),
  music: new Collection('music'),
  exports: new Collection('exports')
};

module.exports = db;


const db = require('../config/db');

const seedData = () => {
  console.log('Seeding Reel Banao Database with 20 Categories & Demo Templates...');

  const categories = [
    { id: "cat_trending", name: "Trending Reels", hindiName: "ट्रेंडिंग रील्स", templateCount: 420, isFeatured: true },
    { id: "cat_insta_reels", name: "Instagram Reels", hindiName: "इंस्टाग्राम रील्स", templateCount: 380, isFeatured: true },
    { id: "cat_birthday", name: "Birthday", hindiName: "जन्मदिन", templateCount: 260 },
    { id: "cat_love", name: "Love", hindiName: "प्यार व रोमांस", templateCount: 340, isFeatured: true },
    { id: "cat_sad", name: "Sad Status", hindiName: "सैड स्टेटस", templateCount: 190 },
    { id: "cat_attitude", name: "Attitude", hindiName: "एटीट्यूड", templateCount: 310, isFeatured: true },
    { id: "cat_cinematic", name: "Cinematic", hindiName: "सिनेमैटिक", templateCount: 290, isFeatured: true },
    { id: "cat_travel", name: "Travel", hindiName: "सफ़र व व्लॉग", templateCount: 275 },
    { id: "cat_wedding", name: "Wedding", hindiName: "शादी ब्याह", templateCount: 210 },
    { id: "cat_festival", name: "Festival", hindiName: "त्योहार", templateCount: 330 },
    { id: "cat_friendship", name: "Friendship", hindiName: "दोस्ती व यारी", templateCount: 185 },
    { id: "cat_family", name: "Family", hindiName: "परिवार", templateCount: 160 },
    { id: "cat_slideshow", name: "Photo Slideshow", hindiName: "फोटो स्लाइड शो", templateCount: 410 },
    { id: "cat_slowmo", name: "Slow Motion", hindiName: "स्लो मोशन (Smooth)", templateCount: 240, isFeatured: true },
    { id: "cat_beatsync", name: "Beat Sync", hindiName: "बीट सिंक (Dhol & Bass)", templateCount: 520, isFeatured: true },
    { id: "cat_motivation", name: "Motivation", hindiName: "मोटिवेशन", templateCount: 195 },
    { id: "cat_nature", name: "Nature", hindiName: "कुदरत व हरियाली", templateCount: 170 },
    { id: "cat_business", name: "Business", hindiName: "बिज़नेस व प्रोमो", templateCount: 140 },
    { id: "cat_yt_shorts", name: "YouTube Shorts", hindiName: "यूट्यूब शॉर्ट्स", templateCount: 310 },
    { id: "cat_insta_stories", name: "Instagram Stories", hindiName: "इंस्टाग्राम स्टोरीज़", templateCount: 280 }
  ];

  db.categories.data = categories;
  db.categories._save();

  const templates = [
    {
      id: "tmpl_01",
      title: "Cyber Beat Drop 3D",
      categoryId: "cat_beatsync",
      categoryName: "Beat Sync",
      coverUrl: "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600",
      slots: [
        { slotIndex: 0, durationMs: 1800, label: "Intro Shot" },
        { slotIndex: 1, durationMs: 1200, label: "Beat 1" },
        { slotIndex: 2, durationMs: 900, label: "Beat 2" },
        { slotIndex: 3, durationMs: 1100, label: "Beat 3" },
        { slotIndex: 4, durationMs: 3000, label: "Drop Climax" }
      ],
      totalDurationMs: 8000,
      usesCount: 142000,
      likesCount: 38400,
      isTrending: true,
      isPopular: true,
      aspectRatio: "9:16",
      musicName: "Neon Bass Drop - DJ Pulse",
      tags: ["Fast Cuts", "Flash", "Bass"]
    },
    {
      id: "tmpl_02",
      title: "Aesthetic Vintage Grain",
      categoryId: "cat_trending",
      categoryName: "Trending Reels",
      coverUrl: "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=600",
      slots: [
        { slotIndex: 0, durationMs: 3500, label: "Memory 1" },
        { slotIndex: 1, durationMs: 3500, label: "Memory 2" },
        { slotIndex: 2, durationMs: 4000, label: "Memory 3" }
      ],
      totalDurationMs: 11000,
      usesCount: 89000,
      likesCount: 21000,
      isTrending: true,
      isLatest: true,
      aspectRatio: "9:16",
      musicName: "Golden Hour Lo-Fi Chill",
      tags: ["Vintage", "Grain", "Lo-Fi"]
    },
    {
      id: "tmpl_03",
      title: "Attitude Mafia BGM 4K",
      categoryId: "cat_attitude",
      categoryName: "Attitude",
      coverUrl: "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=600",
      slots: [
        { slotIndex: 0, durationMs: 2200, label: "Entry Walk" },
        { slotIndex: 1, durationMs: 1800, label: "Glasses Flick" },
        { slotIndex: 2, durationMs: 4000, label: "Slow-Mo Look" }
      ],
      totalDurationMs: 8000,
      usesCount: 210000,
      likesCount: 65000,
      isTrending: true,
      isPopular: true,
      aspectRatio: "9:16",
      musicName: "Villain Theme - Slowed & Reverb",
      tags: ["Attitude", "Slowmo", "Bass"]
    },
    {
      id: "tmpl_04",
      title: "Romantic Sunset Shayari",
      categoryId: "cat_love",
      categoryName: "Love",
      coverUrl: "https://images.unsplash.com/photo-1518199266791-5375a83190b7?w=600",
      slots: [
        { slotIndex: 0, durationMs: 4000, label: "Hand In Hand" },
        { slotIndex: 1, durationMs: 4500, label: "Sunset Smile" },
        { slotIndex: 2, durationMs: 3500, label: "Quote Reveal" }
      ],
      totalDurationMs: 12000,
      usesCount: 67000,
      likesCount: 18500,
      isLatest: true,
      isFavorite: true,
      aspectRatio: "9:16",
      musicName: "Dil Ki Baat - Acoustic Guitar",
      tags: ["Romantic", "Shayari", "Sunset"]
    },
    {
      id: "tmpl_05",
      title: "Himalayan Cinematic Wander",
      categoryId: "cat_cinematic",
      categoryName: "Cinematic",
      coverUrl: "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600",
      slots: [
        { slotIndex: 0, durationMs: 2500, label: "Drone Peak" },
        { slotIndex: 1, durationMs: 3000, label: "Road Trip" },
        { slotIndex: 2, durationMs: 2800, label: "Campfire Fog" },
        { slotIndex: 3, durationMs: 3700, label: "Horizon Peak" }
      ],
      totalDurationMs: 12000,
      usesCount: 115000,
      likesCount: 34000,
      isPopular: true,
      aspectRatio: "9:16",
      musicName: "Epic Mountain Ambience",
      tags: ["4K", "Mountains", "Drone"]
    }
  ];

  db.templates.data = templates;
  db.templates._save();

  const music = [
    {
      id: "aud_01",
      title: "Dholak Bass Drop",
      artist: "DJ Rohit",
      durationMs: 28000,
      audioUrl: "https://example.com/audio1.mp3",
      category: "Beat Sync",
      beatPointsMs: [1200, 2400, 3600, 4800, 7200],
      isTrending: true,
      isFavorite: true,
      usesCount: 89000
    },
    {
      id: "aud_02",
      title: "Midnight Lo-Fi Chill",
      artist: "Acoustic Cafe",
      durationMs: 45000,
      audioUrl: "https://example.com/audio2.mp3",
      category: "Vlog",
      beatPointsMs: [4000, 8000, 16000],
      isTrending: false,
      isFavorite: true,
      usesCount: 54000
    },
    {
      id: "aud_03",
      title: "Phonk Samurai Tokyo",
      artist: "Kenta Drift",
      durationMs: 32000,
      audioUrl: "https://example.com/audio3.mp3",
      category: "Trending",
      beatPointsMs: [800, 1600, 2400, 3200, 4800],
      isTrending: true,
      isFavorite: false,
      usesCount: 142000
    }
  ];

  db.music.data = music;
  db.music._save();

  console.log('Seeding completed successfully!');
};

if (require.main === module) {
  seedData();
}

module.exports = seedData;


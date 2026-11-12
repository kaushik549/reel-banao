# 🚀 Reel Banao - REST API Backend (Node.js & Express)

यह **Reel Banao** ऐप्लिकेशन का **Phase 2 Backend** है। इसे **Node.js** और **Express.js** में बनाया गया है ताकि यह बिना किसी भारी या महंगे पेड क्लाउड सेटअप के **$0 (Zero Cost)** पर आपके कंप्यूटर या किसी भी फ़्री सर्वर (Render, Railway, Fly.io) पर तुरंत चल सके।

---

## 📁 Backend Directory Structure (संरचना)

```text
backend/
├── package.json               # नोड डिपेंडेंसीज़ और रन स्क्रिप्ट्स
├── .env                       # पोर्ट, JWT सीक्रेट और फाइल लिमिट सेटिंग्स
├── .env.example               # कॉन्फ़िगरेशन टेम्पलेट
├── README.md                  # सम्पूर्ण API दस्तावेज़ (Documentation)
├── uploads/                   # अपलोड की गई फाइल्स
│   ├── videos/                # यूज़र वीडियो क्लिप्स
│   ├── images/                # फोटो व कवर्स
│   ├── audio/                 # BGM व म्यूजिक ट्रैक्स
│   └── exports/               # रेंडर किए गए MP4 रील्स
├── data_storage/              # स्केलेबल एम्बेडेड JSON डेटाबेस (Zero Cost)
│   ├── users.json             # यूज़र प्रोफाइल्स
│   ├── templates.json         # 5000+ टेम्प्लेट्स कैटलॉग
│   ├── categories.json        # 20 श्रेणियां
│   ├── projects.json          # यूज़र प्रोजेक्ट ड्राफ्ट्स
│   ├── music.json             # ऑडियो लाइब्रेरी
│   └── exports.json           # एक्सपोर्ट हिस्ट्री
└── src/
    ├── server.js              # सर्वर बूटस्ट्रैप और पोर्ट लिसनर
    ├── app.js                 # एक्सप्रेस ऐप, CORS, हेलमेट व रूट माउंटिंग
    ├── config/
    │   └── db.js              # डेटाबेस इंजन (इंडेक्सिंग व क्वेरी सिस्टम)
    ├── middleware/
    │   ├── auth.js            # JWT प्रमाणीकरण व गेस्ट टोकन
    │   ├── upload.js          # Multer मल्टी-पार्ट वीडियो/इमेज अपलोडर
    │   ├── errorHandler.js    # केंद्रीकृत त्रुटि प्रबंधक
    │   └── validator.js       # इनपुट वैलिडेशन
    ├── controllers/
    │   ├── authController.js       # लॉगिन, रजिस्टर, गेस्ट व प्रोफाइल
    │   ├── templateController.js   # टेम्प्लेट्स, सर्च, फ़िल्टर व 20 श्रेणियां
    │   ├── projectController.js    # प्रोजेक्ट्स (Create, Update, Duplicate, Delete)
    │   ├── mediaController.js      # वीडियो और फोटो अपलोड्स
    │   ├── musicController.js      # म्यूजिक और बीट पॉइंट्स
    │   ├── aiController.js         # AI ऑटो-एडिट सर्विस व कट डिटेक्शन
    │   └── exportController.js     # एक्सपोर्ट रेंडर जॉब्स व स्टेटस
    ├── routes/
    │   ├── authRoutes.js
    │   ├── templateRoutes.js
    │   ├── projectRoutes.js
    │   ├── mediaRoutes.js
    │   ├── musicRoutes.js
    │   ├── aiRoutes.js
    │   └── exportRoutes.js
    ├── services/
    │   └── aiService.js       # AI बीट डिटेक्शन व रिदमिक कट टाइमलाइन जनरेटर
    └── data/
        └── seed.js            # 20 श्रेणियों और टेम्प्लेट्स का इनिशियल डेटा
```

---

## 📡 REST API Endpoints Reference

### 1. Authentication (प्रमाणीकरण)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | नया अकाउंट बनाएं | No |
| `POST` | `/api/v1/auth/login` | ईमेल व पासवर्ड से लॉगिन | No |
| `POST` | `/api/v1/auth/guest` | 1-क्लिक गेस्ट मोड (बिना पासवर्ड) | No |
| `GET` | `/api/v1/auth/profile` | वर्तमान यूज़र की जानकारी | Yes (`Bearer Token`) |

### 2. Templates & Categories (टेम्प्लेट्स और श्रेणियां)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/templates/categories` | सभी 20 श्रेणियां (Trending, Beat Sync आदि) प्राप्त करें |
| `GET` | `/api/v1/templates` | 5000+ टेम्प्लेट्स की पेजिंग, सर्च और फ़िल्टरिंग (`?search=&categoryId=&tab=&page=&limit=`) |
| `GET` | `/api/v1/templates/:id` | किसी विशिष्ट टेम्प्लेट की डिटेल व स्लॉट्स |
| `POST` | `/api/v1/templates/:id/like` | टेम्प्लेट को पसंदीदा (Favorite) टॉगल करें |

### 3. Project Management (प्रोजेक्ट ड्राफ्ट्स)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/projects` | यूज़र के सभी ड्राफ्ट्स प्रोजेक्ट्स |
| `POST` | `/api/v1/projects` | नया प्रोजेक्ट बनाएं (क्लिप्स, टाइमलाइन, आस्पेक्ट रेशियो) |
| `GET` | `/api/v1/projects/:id` | किसी प्रोजेक्ट की पूरी टाइमलाइन प्राप्त करें |
| `PUT` | `/api/v1/projects/:id` | प्रोजेक्ट अपडेट करें (ट्रिम, स्प्लिट, ऑडियो, टेक्स्ट) |
| `POST` | `/api/v1/projects/:id/duplicate` | प्रोजेक्ट की कॉपी बनाएं |
| `DELETE` | `/api/v1/projects/:id` | प्रोजेक्ट डिलीट करें |

### 4. Media Upload (वीडियो व फोटो अपलोड)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/media/upload` | सिंगल वीडियो या फोटो अपलोड करें (Form-data: `media`) |
| `POST` | `/api/v1/media/upload-multiple` | एक साथ 15 फाइल्स तक अपलोड करें |

### 5. Music Library (म्यूजिक व बीट्स)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/music` | ऑडियो ट्रैक्स सूची (`?category=Beat Sync&search=`) |
| `GET` | `/api/v1/music/:id` | किसी ट्रैक की डिटेल व बीट ड्रॉप पॉइंट्स |

### 6. AI Auto Editing (आर्टिफिशियल इंटेलिजेंस ऑटो एडिट)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/ai/styles` | सभी AI एडिटिंग स्टाइल्स (Beat-Punch, Cinematic, Retro) |
| `POST` | `/api/v1/ai/auto-edit` | मीडिया क्लिप्स दें, AI खुद बीट मैच करके टाइमलाइन तैयार करेगा |

### 7. Video Export (एक्सपोर्ट और रेंडर)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/export/start` | 720p/1080p/4K एक्सपोर्ट जॉब शुरू करें |
| `GET` | `/api/v1/export/status/:jobId` | रेंडर प्रोग्रेस व फाइनल MP4 डाउनलोड लिंक |

---

## 💻 बैकएंड को कैसे चलाएं (How to Run Backend)

1. टर्मिनल या कमांड प्रॉम्प्ट खोलें और `backend` डायरेक्टरी में जाएं:
   ```bash
   cd "c:\Users\RAHUL\Desktop\reel banao\backend"
   ```
2. सर्वर शुरू करने के लिए कमांड चलाएं:
   ```bash
   npm start
   ```
   या लाइव ऑटो-रीलोड के लिए:
   ```bash
   npm run dev
   ```
3. सर्वर **`http://localhost:5000`** पर चालू हो जाएगा।
4. ब्राउज़र में **`http://localhost:5000/api/health`** खोलकर आप जांच सकते हैं!


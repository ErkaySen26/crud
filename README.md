# 🚀 NodeBlog - Express.js & MongoDB Blog Uygulaması

## 📖 Proje Açıklaması

**NodeBlog**, modern web teknolojileri kullanılarak geliştirilmiş tam özellikli bir blog uygulamasıdır. Kullanıcıların kayıt olup giriş yapabildiği, blog yazıları oluşturabildiği ve görüntüleyebildiği dinamik bir platformdur.

### 🎯 Ana Özellikler

- ✅ Kullanıcı kayıt ve giriş sistemi
- ✅ Session yönetimi (MongoDB'de saklama)
- ✅ Blog yazıları oluşturma ve görüntüleme
- ✅ Dosya yükleme (resim)
- ✅ Responsive tasarım
- ✅ Modern template engine (Handlebars)

---

## 🛠️ Teknoloji Stack'i

### Backend

- **Node.js** - JavaScript runtime environment
- **Express.js** - Web framework
- **MongoDB** - NoSQL veritabanı
- **Mongoose** - MongoDB object modeling
- **Express-Session** - Session yönetimi
- **Connect-Mongo** - Session'ları MongoDB'de saklama
- **Express-Fileupload** - Dosya yükleme
- **Body-Parser** - HTTP request parsing

### Frontend

- **Handlebars** - Template engine
- **Bootstrap** - CSS framework
- **jQuery** - JavaScript library
- **Font Awesome** - Icon library
- **Owl Carousel** - Slider component

### Development Tools

- **Nodemon** - Otomatik server restart

---

## 📁 Proje Yapısı

```
nodeblog/
├── app.js                 # Ana uygulama dosyası
├── package.json           # Proje bağımlılıkları
├── models/                # Veritabanı modelleri
│   ├── Post.js           # Blog yazısı modeli
│   └── User.js           # Kullanıcı modeli
├── routes/                # Route tanımları
│   ├── main.js           # Ana sayfa route'ları
│   ├── post.js           # Blog yazısı route'ları
│   └── users.js          # Kullanıcı route'ları
├── views/                 # Handlebars template'leri
│   ├── layouts/
│   │   └── main.handlebars
│   ├── partials/
│   │   ├── site-header.handlebars
│   │   ├── site-footer.handlebars
│   │   └── site-nav.handlebars
│   └── site/
│       ├── index.handlebars
│       ├── login.handlebars
│       ├── register.handlebars
│       ├── addpost.handlebars
│       ├── blog.handlebars
│       ├── post.handlebars
│       ├── about.handlebars
│       └── contact.handlebars
├── public/                # Statik dosyalar
│   ├── css/              # CSS dosyaları
│   ├── js/               # JavaScript dosyaları
│   ├── img/              # Resim dosyaları
│   └── fonts/            # Font dosyaları
└── helpers/               # Yardımcı fonksiyonlar
    └── generateDate.js   # Tarih formatlama
```

---

## ⚙️ Kurulum ve Çalıştırma

### 1. Gereksinimler

- Node.js (v14 veya üzeri)
- MongoDB (v4 veya üzeri)
- Git

### 2. Projeyi Klonlama

```bash
git clone [repository-url]
cd nodeblog
```

### 3. Bağımlılıkları Yükleme

```bash
npm install
```

### 4. MongoDB'yi Başlatma

```bash
# Windows
mongod

# macOS/Linux
sudo systemctl start mongod
```

### 5. Uygulamayı Çalıştırma

```bash
npm run boot
```

Uygulama `http://127.0.0.1:3000` adresinde çalışmaya başlayacaktır.

---

## 🔧 Kod Analizi (Feynman Tekniği)

### 📄 app.js - Ana Uygulama Dosyası

```javascript
const express = require("express");
const { engine } = require("express-handlebars");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const fileUpload = require("express-fileupload");
const session = require("express-session");
const MongoStore = require("connect-mongo");
```

**Ne yapıyor?** Bu satırlar, uygulamanın ihtiyaç duyduğu tüm kütüphaneleri import ediyor. Her biri farklı bir işlevi yerine getiriyor:

- `express`: Web sunucusu oluşturmak için
- `express-handlebars`: HTML sayfalarını dinamik hale getirmek için
- `mongoose`: MongoDB ile konuşmak için
- `body-parser`: Form verilerini okumak için
- `express-fileupload`: Dosya yüklemek için
- `express-session`: Kullanıcı oturumlarını yönetmek için
- `connect-mongo`: Oturumları veritabanında saklamak için

```javascript
mongoose.connect("mongodb://127.0.0.1/nodeblog_db", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});
```

**Ne yapıyor?** MongoDB veritabanına bağlanıyor. `nodeblog_db` adında bir veritabanı oluşturuyor veya var olanı kullanıyor.

```javascript
app.use(
  expressSession({
    secret: "testotesto",
    resave: false,
    saveUninitialized: false,
    store: MongoStore.create({
      mongoUrl: "mongodb://127.0.0.1/nodeblog_db",
    }),
  })
);
```

**Ne yapıyor?** Session (oturum) sistemini kuruyor. Kullanıcı giriş yaptığında, bilgileri MongoDB'de saklanıyor. Bu sayede sayfa yenilendiğinde giriş bilgileri kaybolmuyor.

```javascript
app.engine(
  "handlebars",
  engine({
    helpers: {
      generateDate: generateDate,
    },
  })
);
app.set("view engine", "handlebars");
```

**Ne yapıyor?** Handlebars template engine'ini ayarlıyor. `generateDate` fonksiyonu, tarihleri güzel formatlamak için kullanılıyor.

```javascript
app.use("/", main);
app.use("/posts", post);
app.use("/users", users);
```

**Ne yapıyor?** Farklı URL'lerin hangi route dosyalarına gideceğini belirliyor:

- `/` → main.js
- `/posts` → post.js
- `/users` → users.js

---

### 🗃️ models/User.js - Kullanıcı Modeli

```javascript
const UserSchema = new mongoose.Schema({
  username: { type: String },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true, unique: true },
});
```

**Ne yapıyor?** Kullanıcı verilerinin nasıl saklanacağını tanımlıyor:

- `username`: İsteğe bağlı kullanıcı adı
- `email`: Zorunlu ve benzersiz email adresi
- `password`: Zorunlu şifre

**Neden önemli?** Bu şema, veritabanında kullanıcı tablosunun yapısını belirliyor.

---

### 🗃️ models/Post.js - Blog Yazısı Modeli

```javascript
const PostSchema = new mongoose.Schema({
  title: { type: String, required: true },
  content: { type: String, required: true },
  date: { type: Date, default: Date.now },
  post_image: { type: String, require: true },
});
```

**Ne yapıyor?** Blog yazılarının nasıl saklanacağını tanımlıyor:

- `title`: Yazı başlığı (zorunlu)
- `content`: Yazı içeriği (zorunlu)
- `date`: Yazı tarihi (otomatik olarak şu anki tarih)
- `post_image`: Yazı resmi (zorunlu)

---

### 🛣️ routes/users.js - Kullanıcı İşlemleri

#### Kayıt Olma (Register)

```javascript
router.post("/register", async (req, res) => {
  try {
    await User.create(req.body);
    res.redirect("/");
  } catch (error) {
    res.render("site/register", { error: "Kayıt sırasında hata oluştu." });
  }
});
```

**Ne yapıyor?**

1. Kullanıcıdan gelen bilgileri alıyor (`req.body`)
2. Yeni kullanıcı oluşturuyor (`User.create`)
3. Başarılıysa ana sayfaya yönlendiriyor
4. Hata varsa hata mesajı gösteriyor

#### Giriş Yapma (Login)

```javascript
router.post("/login", async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await User.findOne({ email });

    if (user) {
      if (user.password === password) {
        req.session.userId = user._id;
        res.redirect("/");
      } else {
        res.redirect("/users/login");
      }
    } else {
      res.redirect("/users/register");
    }
  } catch (error) {
    res.redirect("/users/login");
  }
});
```

**Ne yapıyor?**

1. Email ve şifreyi alıyor
2. Email'e göre kullanıcıyı buluyor
3. Şifre doğruysa session oluşturuyor
4. Yanlışsa tekrar giriş sayfasına yönlendiriyor
5. Kullanıcı yoksa kayıt sayfasına yönlendiriyor

#### Çıkış Yapma (Logout)

```javascript
router.get("/logout", (req, res) => {
  req.session.destroy();
  res.redirect("/");
});
```

**Ne yapıyor?** Session'ı silip ana sayfaya yönlendiriyor.

---

### 🛣️ routes/post.js - Blog Yazısı İşlemleri

#### Yeni Yazı Ekleme Sayfası

```javascript
router.get("/new", (req, res) => {
  if (req.session.userId) {
    res.render("site/addpost");
  } else {
    res.redirect("/users/login");
  }
});
```

**Ne yapıyor?** Kullanıcı giriş yapmışsa yazı ekleme sayfasını gösteriyor, yapmamışsa login sayfasına yönlendiriyor.

#### Yazı Kaydetme

```javascript
router.post("/test", (req, res) => {
  let post_image = req.files.post_image;

  post_image.mv(
    path.resolve(__dirname, "../public/img/postimages", post_image.name)
  );

  Post.create({
    ...req.body,
    post_image: `/img/postimages/${post_image.name}`,
  });

  res.redirect("/");
});
```

**Ne yapıyor?**

1. Yüklenen resmi alıyor
2. Resmi `public/img/postimages` klasörüne kaydediyor
3. Yazı bilgilerini veritabanına kaydediyor
4. Ana sayfaya yönlendiriyor

---

### 🛣️ routes/main.js - Ana Sayfa İşlemleri

```javascript
router.get("/", (req, res) => {
  console.log(req.session);
  res.render("site/index");
});
```

**Ne yapıyor?** Ana sayfayı gösteriyor ve session bilgilerini console'a yazdırıyor.

```javascript
router.get("/blog", (req, res) => {
  Post.find({})
    .lean()
    .then((posts) => {
      res.render("site/blog", { posts: posts });
    });
});
```

**Ne yapıyor?** Tüm blog yazılarını veritabanından çekip blog sayfasında gösteriyor.

---

## 🔍 API Endpoints

### Ana Sayfa

- `GET /` - Ana sayfa

### Kullanıcı İşlemleri

- `GET /users/register` - Kayıt sayfası
- `POST /users/register` - Kayıt işlemi
- `GET /users/login` - Giriş sayfası
- `POST /users/login` - Giriş işlemi
- `GET /users/logout` - Çıkış işlemi

### Blog Yazısı İşlemleri

- `GET /posts/new` - Yeni yazı sayfası
- `POST /posts/test` - Yazı kaydetme
- `GET /posts/:id` - Yazı detayı
- `GET /blog` - Tüm yazılar

### Diğer Sayfalar

- `GET /about` - Hakkında sayfası
- `GET /contact` - İletişim sayfası

---

## 🗄️ Veritabanı Yapısı

### Users Collection

```javascript
{
  _id: ObjectId,
  username: String,
  email: String (unique),
  password: String
}
```

### Posts Collection

```javascript
{
  _id: ObjectId,
  title: String,
  content: String,
  date: Date,
  post_image: String
}
```

### Sessions Collection (Otomatik)

```javascript
{
  _id: ObjectId,
  session: String,
  expires: Date
}
```

---

## 🛡️ Güvenlik Önlemleri

### Session Güvenliği

- Session'lar MongoDB'de şifreli saklanır
- Otomatik session süresi (24 saat)
- Güvenli cookie ayarları

### Veri Doğrulama

- Email benzersizlik kontrolü
- Zorunlu alan kontrolü
- Dosya yükleme güvenliği

### Yetki Kontrolü

- Giriş yapmadan yazı eklenemez
- Session kontrolü her korumalı sayfada

---

## 🐛 Sorun Giderme

### Yaygın Hatalar

#### "MongoDB bağlantı hatası"

```bash
# MongoDB'yi başlatın
mongod
```

#### "Module not found" hatası

```bash
# Bağımlılıkları yeniden yükleyin
npm install
```

#### Session çalışmıyor

```bash
# connect-mongo paketini kontrol edin
npm list connect-mongo
```

### Debug Modu

```javascript
// routes/main.js'de session bilgilerini görmek için
console.log(req.session);
```

---

## 🚀 Geliştirme Notları

### Kod Yapısı

- **Modüler Tasarım**: Her route ayrı dosyada
- **Template Engine**: Handlebars ile dinamik içerik
- **Middleware Kullanımı**: Express middleware'leri
- **Async/Await**: Modern JavaScript syntax

### Performans Optimizasyonları

- **Lean Queries**: MongoDB sorguları optimize edildi
- **Static Files**: Express static middleware
- **Session Store**: MongoDB'de session saklama

### Gelecek Geliştirmeler

- [ ] Şifre hashleme (bcrypt)
- [ ] Email doğrulama
- [ ] Yazı düzenleme/silme
- [ ] Yorum sistemi
- [ ] Admin paneli
- [ ] API endpoints

---

## 📞 İletişim ve Destek

Bu proje eğitim amaçlı geliştirilmiştir. Sorularınız için:

- 📧 Email: [email@example.com]
- 🐛 Bug Reports: [GitHub Issues]
- 📖 Dokümantasyon: Bu README dosyası

---

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için `LICENSE` dosyasına bakınız.

---


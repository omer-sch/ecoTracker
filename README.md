# EcoTracker 🌱

> **A social network for green living — share eco tips, track sustainability goals, and connect with an environmentally-conscious community**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android_API_25--34-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=black)](https://firebase.google.com)
[![Room](https://img.shields.io/badge/Room_DB-4479A1?style=flat-square&logo=sqlite&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

**EcoTracker** is a full-featured Android social platform built around environmental sustainability. Users share eco-friendly tips, set and complete personal green goals, follow friends, and stay informed with live air quality data — all backed by Firebase and a local Room cache for offline support.

---

## Features

- **Social Feed** — Browse eco posts from the community with full-view support and image uploads
- **Friends System** — Send friend requests, follow other users, view friend posts and achievements
- **Notifications** — Real-time alerts for friend requests and activity, with swipe-to-delete
- **Eco Tips** — Browse and save community-curated environmental tips to a personal favorites list
- **Goals Tracker** — Set personal sustainability goals, mark them complete, and view friends' goals
- **Photo Gallery** — Upload and browse profile photos with full-screen viewer
- **Air Quality** — Live AQI data via REST API with color-coded indicators
- **User Search** — Find and connect with other users across the platform
- **Profile Management** — Edit name, bio, profile photo, and password from a dedicated settings screen
- **Forgot Password** — Email-based password reset flow via Firebase Auth

---

## Architecture

```
┌───────────────────────────────────────────────────────────┐
│                     Android App (Single Activity)          │
│                                                           │
│  StartFragment ──► LoginFragment / RegisterFragment       │
│                           │                               │
│                    FeedFragment (main hub)                │
│                    ┌──────┴──────────────────────┐        │
│              Posts │      Profile   │    Tips     │        │
│         AddPost    │  EditProfile   │  TipsVM     │        │
│         FriendPosts│  MyGoals       │  MyTips     │        │
│         PostFull   │  MyPosts       │             │        │
│                    │  MyPhotos      │             │        │
│                    │  MyAlerts      │             │        │
│                    │  ProfileOther  │             │        │
│                    │  UserSearch    │             │        │
│                    └────────────────┴─────────────┘        │
│                AirQualityFragment  (REST API)              │
└───────────────────────┬───────────────────────────────────┘
                        │
          ┌─────────────┴─────────────┐
          ▼                           ▼
   Model.kt (Singleton)         ViewModel layer
   ┌───────┴──────────┐         (ProfileVM, TipsVM,
   ▼                  ▼          UserSearchVM, PostsVM,
Room DB           Firebase        ProfileFriendVM)
(local cache)    (cloud sync)
  Posts            Firestore
  Users            Auth
  Tips             Storage
  Goals            Notifications
  MyTips
  OtherUsers
```

### Data Flow

```
UI Fragment → ViewModel → Model.kt (singleton)
                              │
              ┌───────────────┴───────────────┐
              ▼                               ▼
         Room DB (local)              FirebaseManager
         Instant reads               Cloud reads/writes
         Offline support             Real-time updates
              │                               │
              └───────────── sync ────────────┘
                   lastUpdated timestamp-based
                   incremental delta fetching
```

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| Architecture | Single Activity + Navigation Component (multiple Fragments) |
| UI | Material Design, ConstraintLayout, RecyclerView |
| Authentication | Firebase Authentication (email/password + forgot password) |
| Cloud Database | Firebase Firestore |
| File Storage | Firebase Cloud Storage |
| Local Cache | Room Database (SQLite) |
| Threading | Executors thread pool + HandlerCompat (main thread callbacks) |
| Image Loading | Picasso |
| REST API | HttpURLConnection — Air Quality Index (AQI) |
| Build | Gradle |
| Min SDK | API 25 (Android 7.1) |
| Target SDK | API 34 (Android 14) |

---

## Project Structure

```
app/src/main/java/com/example/greenapp/
│
├── MainActivity.kt                    # Single activity, nav controller host
├── FeedFragment.kt                    # Main social feed
├── StartFragment.kt                   # Splash / auth gate
├── BaseFragment.kt                    # Shared fragment behavior
│
├── Modules/
│   ├── Auth/
│   │   ├── LoginFragment.kt
│   │   └── RegisterFragment.kt
│   ├── Posts/
│   │   ├── AddPostFragment.kt         # Create post with image upload
│   │   ├── PostFullViewFragment.kt    # Full post detail view
│   │   ├── FriendPosts.kt            # Friend's post feed
│   │   └── FriendAchievements.kt     # Friend goal completions
│   ├── Profile/
│   │   ├── ProfileFragment.kt         # Own profile (posts/tips/goals/photos tabs)
│   │   ├── ProfileViewFragment.kt     # Other user's profile
│   │   ├── EditProfileFragment.kt     # Edit name, bio, photo
│   │   ├── MyGoalsFragment.kt         # Personal goals CRUD + complete flow
│   │   ├── MyGoalCompleteFragment.kt
│   │   ├── MyPostsFragment.kt
│   │   ├── MyTipsFragment.kt          # Saved favorite tips
│   │   ├── MyPhotosFragment.kt        # Photo gallery
│   │   ├── MyPhotoFullFragment.kt     # Full-screen photo viewer
│   │   ├── MyAlertsFragment.kt        # Friend request notifications
│   │   ├── ProfileViewModel.kt
│   │   └── ProfileFriendViewModel.kt
│   ├── Tips/
│   │   ├── TipsFragment.kt
│   │   └── TipsViewModel.kt
│   ├── Common/
│   │   ├── UserSearchFragment.kt
│   │   └── UserSearchViewModel.kt
│   └── AirQuality/
│       ├── AirQualityFragment.kt      # Live AQI with color indicators
│       └── AirQualityAPI.kt           # REST client for AQI data
│
├── adapters/                          # RecyclerView adapters
│   ├── PostsRecyclerAdapter.kt
│   ├── GoalsAdapter.kt
│   ├── FriendGoalsAdapter.kt
│   ├── TipsAdapter.kt
│   ├── AlertsAdapter.kt
│   ├── PhotosAdapter.kt
│   └── UsersAdapter.kt
│
├── database/
│   ├── Model.kt                       # Central data singleton — bridges Room ↔ Firebase
│   ├── local/
│   │   ├── AppLocalDb.kt              # Room database definition
│   │   ├── Converters.kt              # Type converters for Room
│   │   ├── PostDao.kt
│   │   ├── UserDao.kt
│   │   ├── TipsDao.kt
│   │   ├── MyTipsDao.kt
│   │   ├── GoalsDao.kt
│   │   └── OtherUserDao.kt
│   └── remote/
│       └── FirebaseManager.kt         # All Firebase read/write operations
│
├── models/
│   ├── Post.kt
│   ├── User.kt
│   ├── Tip.kt
│   ├── MyTip.kt
│   ├── Goal.kt
│   ├── OtherUser.kt
│   ├── FriendNotification.kt
│   └── FriendNotificationPopulated.kt
│
├── network/
│   └── HttpPostClient.kt              # HTTP client for REST API calls
│
└── base/
    └── MyApplication.kt               # Application class, DB initialization
```

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Android device or emulator (API 25+)
- A Firebase project with **Authentication**, **Firestore**, and **Cloud Storage** enabled

### Setup

1. **Clone the repo**

```bash
git clone https://github.com/omer-sch/ecoTracker.git
cd ecoTracker
```

2. **Connect Firebase**

   - Go to [Firebase Console](https://console.firebase.google.com) → your project → Project Settings
   - Download `google-services.json`
   - Place it in `app/google-services.json`

3. **Configure Firestore Rules**

```js
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

4. **Configure Storage Rules**

```js
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

5. **Open in Android Studio** → sync Gradle → run on device or emulator

---

## Firebase Data Model

```
users/
  {uid}/
    name, email, profileUri, bio, friendIds[]

posts/
  {postId}/
    name, description, imageUri, userId, timestamp, lastUpdated

tips/
  {tipId}/
    title, description, category, timestamp

notifications/
  {userId}/
    friendRequests[]
      senderId, senderName, status, timestamp
```

---

## Key Design Decisions

**Singleton Model Pattern** — `Model.kt` is the single source of truth for all data operations. Fragments and ViewModels never touch Firebase or Room directly — they go through Model.

**Dual-layer sync** — Reads always hit Room first (instant, offline-capable). Firebase is synced in the background using `lastUpdated` timestamps so only changed records are fetched — not full collections.

**Threading** — All background work runs on a dedicated `Executors` thread pool. Results are posted back to the main thread via `HandlerCompat` to update the UI safely.

**Single Activity navigation** — Android Navigation Component manages all fragment transitions. The nav graph enforces the auth gate (StartFragment) before reaching any content screen.

---

## Built By

**Omer Schreiber** — Designed and built end-to-end as a full-featured Android social platform.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Omer_Schreiber-0077B5?style=flat-square&logo=linkedin)](https://linkedin.com/in/omer-schreiber-48b3912b6)
[![GitHub](https://img.shields.io/badge/GitHub-omer--sch-181717?style=flat-square&logo=github)](https://github.com/omer-sch)

---

## License

MIT — see [LICENSE](LICENSE) for details.

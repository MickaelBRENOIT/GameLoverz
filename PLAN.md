# Plan de configuration du projet Android – GameLoverz

> Ce fichier sert de TODO global pour configurer la **base technique** du projet.
> Chaque case doit être cochée **uniquement après vérification manuelle**.

---

## Étape 0 – Préparation de l’environnement

### Objectif

S’assurer que l’environnement de dev est prêt avant de créer le projet.

### Actions

- [x] Installer / Mettre à jour **Android Studio** vers la dernière version stable
- [x] Vérifier que le **JDK 17** est bien utilisé par Android Studio
- [x] Installer le **SDK Android 31+** (Android 12) dans le SDK Manager
- [x] Créer / vérifier un compte **Twitch Developer** (pour IGDB)
- [x] Créer / vérifier un compte **Supabase** (projet vide OK pour l’instant)

### Vérifications manuelles

- [x] Android Studio démarre correctement et peut créer un projet Kotlin
- [x] Un projet Supabase est accessible depuis le dashboard web
- [x] Un compte « Twitch Developer Application » est créé dans la console Twitch

---

## Étape 1 – Création du projet Android de base

### Objectif

Créer le squelette du projet Android avec les bons paramètres de base.

### Actions

- [x] Créer un projet Android :
  - [x] Nom du projet : `GameLoverz` (ou équivalent)
  - [x] Package name : `com.gameloverz.app` (ou autre, mais fixé)
  - [x] Langage : **Kotlin**
  - [x] Minimum SDK : **Android 12 (API 31)**
  - [x] Cocher : **Use Jetpack Compose**
- [x] Configurer le contrôle de version :
  - [x] Initialiser **Git** dans le dossier du projet
  - [x] Créer un premier commit : `chore: initial android project`

### Vérifications manuelles

- [x] L’application compile et se lance sur un émulateur ou un device
- [x] Un écran par défaut (template Android) s’affiche sans crash
- [x] `git status` indique un working directory propre

---

## Étape 2 – Configuration Gradle (racine)

### Objectif

Avoir une config Gradle propre et standard pour le projet.

### Actions

- [x] Ouvrir `settings.gradle` / `settings.gradle.kts` et vérifier :
  - [x] `google()` et `mavenCentral()` sont bien présents dans `repositories`
- [x] Ouvrir le `build.gradle` / `build.gradle.kts` racine et vérifier / ajouter :
  - [x] Plugin `com.android.application` (AGP) déclaré avec version stable
  - [x] Plugin `org.jetbrains.kotlin.android` déclaré avec version stable
  - [x] Plugin `com.google.dagger.hilt.android` déclaré (apply false)

### Vérifications manuelles

- [x] Le projet **sync Gradle** sans erreur
- [x] Aucune erreur rouge dans la fenêtre Gradle ou dans le `build.gradle` racine

---

## Étape 3 – Configuration du module `app`

### Objectif

Configurer correctement le module application pour Compose, Java 17, etc.

### Actions

- [x] Dans `app/build.gradle(.kts)`, vérifier / configurer :
  - [x] `namespace = "com.gameloverz.app"` (ou namespace choisi)
  - [x] `compileSdk` = dernière version supportée par l’AGP (ex 34/35)
  - [x] `minSdk = 31`
  - [x] `targetSdk` = même valeur que `compileSdk`
- [x] Configurer Java/Kotlin :
  - [x] `sourceCompatibility = JavaVersion.VERSION_17`
  - [x] `targetCompatibility = JavaVersion.VERSION_17`
  - [x] `kotlinOptions.jvmTarget = "17"`
- [ ] Activer Compose :
  - [x] `buildFeatures { compose = true }`

### Vérifications manuelles

- [x] Sync Gradle OK après modification
- [x] Build du projet OK
- [x] L’app se lance toujours sur un device sans crash

---

## Étape 4 – Plugins et dépendances de base

### Objectif

Installer les plugins et dépendances essentielles (Compose, Lifecycle, Navigation, Coroutines).

### Actions

- [x] Ajouter les plugins dans `app/build.gradle(.kts)` :
  - [x] `id("com.android.application")`
  - [x] `id("org.jetbrains.kotlin.android")`
  - [x] `id("com.google.dagger.hilt.android")`
  - [x] `kotlin("kapt")`
- [x] Ajouter la **Compose BOM** dans `dependencies`
- [x] Ajouter les dépendances Compose :
  - [x] `androidx.compose.ui:ui`
  - [x] `androidx.compose.material3:material3`
  - [x] `androidx.compose.ui:ui-tooling-preview`
  - [x] `androidx.compose.foundation:foundation`
- [x] Ajouter les dépendances Activity/Compose :
  - [x] `androidx.activity:activity-compose`
- [x] Ajouter Lifecycle / ViewModel :
  - [x] `lifecycle-runtime-ktx`
  - [x] `lifecycle-viewmodel-compose`
- [x] Ajouter Navigation Compose :
  - [x] `androidx.navigation:navigation-compose`
- [x] Ajouter Coroutines :
  - [x] `kotlinx-coroutines-core`
  - [x] `kotlinx-coroutines-android`

### Vérifications manuelles

- [x] Sync Gradle OK
- [x] Création d’un simple écran Compose dans `MainActivity` fonctionne
- [x] L’app démarre et affiche un composable « Hello GameLoverz » (ou équivalent)

---

## Étape 5 – Mise en place de Hilt (DI)

### Objectif

Configurer l’injection de dépendances Hilt pour le projet.

### Actions

- [x] Ajouter les dépendances Hilt :
  - [x] `com.google.dagger:hilt-android`
  - [x] `com.google.dagger:hilt-android-compiler` avec `kapt`
  - [x] `androidx.hilt:hilt-navigation-compose`
- [x] Créer une classe `GameLoverzApp : Application` annotée avec :
  - [x] `@HiltAndroidApp`
- [x] Déclarer `android:name=".GameLoverzApp"` dans le `AndroidManifest.xml` (balise `<application>`)
- [x] Annoter l’`Activity` principale avec :
  - [x] `@AndroidEntryPoint`

### Vérifications manuelles

- [x] Sync Gradle OK
- [x] Build OK
- [x] L’app se lance avec Hilt activé (pas d’erreur « Hilt Android App not found »)
- [x] Ajout d’un ViewModel anoté `@HiltViewModel` + injection d’une dépendance simple fonctionne (test minimal)

---

## Étape 6 – Squelette de navigation Compose

### Objectif

Mettre en place la navigation de base (graph & bottom bar) sans logique métier.

### Actions

- [ ] Créer un composable `GameLoverzApp()` qui sera le root de l’app
- [ ] Installer `NavHost` + `NavController` dans `GameLoverzApp()`
- [ ] Définir au minimum 3 routes :
  - [ ] `collection`
  - [ ] `wishlist`
  - [ ] `settings`
- [ ] Créer une **bottom navigation bar** qui permet de :
  - [ ] Naviguer vers `collection`
  - [ ] Naviguer vers `wishlist`
  - [ ] Naviguer vers `settings`
- [ ] Utiliser des écrans placeholders :
  - [ ] `CollectionScreen()` – affiche juste un texte « Collection »
  - [ ] `WishlistScreen()` – affiche « Wishlist »
  - [ ] `SettingsScreen()` – affiche « Paramètres »

### Vérifications manuelles

- [ ] L’app démarre directement sur un des écrans (par ex. `collection`)
- [ ] Un clic sur chaque item de la bottom bar change bien d’écran
- [ ] Pas de crash en navigation (back, rotation éventuelle)

---

## Étape 7 – Supabase (Auth + Postgres) – setup minimal

### Objectif

Préparer la connexion Supabase pour l’auth et la base de données.

### Actions

- [ ] Ajouter le BOM `supabase-kt` dans `dependencies`
- [ ] Ajouter les modules nécessaires :
  - [ ] `auth-kt` (authentification)
  - [ ] `postgrest-kt` (accès aux tables)
  - [ ] `ktor-client-android` (ou autre engine pour Supabase-kt)
- [ ] Créer un fichier `SupabaseClientProvider` (ou équivalent) qui :
  - [ ] Initialise un client Supabase avec l’URL du projet
  - [ ] Utilise la `anon key` (clé publique)
- [ ] Intégrer ce client dans Hilt (ex : module `SupabaseModule` avec `@Provides`)

### Vérifications manuelles

- [ ] Sync Gradle OK
- [ ] Un test simple de connexion (ex. appel à `auth.retrieveUser()` ou un `select` sur une table de test) fonctionne sans crash
- [ ] Les logs indiquent une connexion Supabase réussie

---

## Étape 8 – Dépendances réseau & client IGDB

### Objectif

Préparer l’accès à l’API IGDB (via Retrofit et un futur backend ou direct, à définir).

### Actions

- [ ] Ajouter les dépendances Retrofit :
  - [ ] `retrofit`
  - [ ] un converter JSON (Moshi ou Gson)
- [ ] Ajouter les dépendances OkHttp :
  - [ ] `okhttp`
  - [ ] `logging-interceptor`
- [ ] Créer une interface `IgdbApi` (même vide pour l’instant) qui contiendra les endpoints
- [ ] Créer une classe de configuration réseau (ex : `NetworkModule`) pour :
  - [ ] Fournir un `OkHttpClient` avec logging
  - [ ] Fournir un `Retrofit` de base (baseUrl placeholder pour l’instant)
- [ ] Prévoir dans le code un TODO clair sur la gestion :
  - [ ] du `client_id` / `client_secret` Twitch
  - [ ] de l’accès (quel côté ? backend / direct ?)

### Vérifications manuelles

- [ ] Sync Gradle OK
- [ ] Création d’un client Retrofit ne provoque pas de crash
- [ ] Un appel « fake » (ex. endpoint qui n’existe pas mais géré) n’explose pas l’app (gestion d’erreur basique)

---

## Étape 9 – Stockage local & préférences (DataStore)

### Objectif

Installer un système pour stocker quelques préférences locales (ex : thème).

### Actions

- [ ] Ajouter la dépendance :
  - [ ] `androidx.datastore:datastore-preferences`
- [ ] Créer un wrapper `UserPreferencesRepository` qui :
  - [ ] Expose un `Flow` pour un thème (light/dark/system)
  - [ ] Permet d’écrire une préférence de thème
- [ ] Intégrer ce repo via Hilt

### Vérifications manuelles

- [ ] Synchronisation Gradle OK
- [ ] Changer la valeur du thème (via un bouton de debug ou dans `SettingsScreen`) reflète bien la modification au redémarrage de l’app

---

## Étape 10 – Outils visuels et logging (Coil + Timber)

### Objectif

Pouvoir charger des images (jaquettes) et logger proprement.

### Actions

- [ ] Ajouter la dépendance `io.coil-kt:coil-compose`
- [ ] Ajouter la dépendance `com.jakewharton.timber:timber`
- [ ] Initialiser `Timber.plant(...)` dans `GameLoverzApp.onCreate()`
- [ ] Tester l’affichage d’une image avec `AsyncImage` (URL de test)

### Vérifications manuelles

- [ ] Une image de test s’affiche correctement via Coil
- [ ] Les logs apparaissent bien dans Logcat via Timber (log de test)

---

## Étape 11 – Vérification globale de la base projet

### Objectif

S’assurer que la base technique est propre avant d’attaquer les features métier (collection, wishlist).

### Vérifications manuelles

- [ ] Le projet compile sans erreur
- [ ] L’app démarre sans crash sur un device physique ou émulateur
- [ ] La navigation de base (Collection / Wishlist / Paramètres) fonctionne
- [ ] Hilt est opérationnel (injection d’un ViewModel test OK)
- [ ] Supabase est accessible (appel simple OK)
- [ ] Le client réseau (Retrofit) est initialisé correctement (pas d’erreur au démarrage)
- [ ] DataStore lit/écrit une préférence simple
- [ ] Coil affiche une image de test
- [ ] Git contient un commit clair pour chaque étape importante (setup, Hilt, Navigation, Supabase, etc.)

---

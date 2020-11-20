# Docchi

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview
### Description
Docchi is a voting app that lets users to create public polls. It will let users create quick large scale surveys and polls to make decisions. All votes on our platform will be anonymous.

### App Evaluation
- **Category:** Social Networking and Survey
- **Mobile:** Docchi would be primarily developed for mobile as it would allow users to use their camera, take a picture, and add it to their poll. However, a web counterpart can also be developed like many other apps.
- **Story:** A user who is working implementing a new feature, shopping, or needs advice can quickly create a poll to assist them make a decision.
- **Market:** Any individual could choose to use this app, and to keep it a safe environment, people would be organized into age groups.
- **Habit:** This app could be used anytime a user is needs an option, or if they want to assist anyone else make a decision.
- **Scope:** We will start with allowing users to create create a poll and share it with the entire Docchi community. In the future, we will let users create groups so that they can only get advice from certain people, categorize polls, and add augment reality features.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can login 
* User can create an account
* User can view their timeline
* User can scroll through the posts (infinite scroll)
* User can create a post
* User can vote
* User can view their own posts 
* User can view profile details by clicking on profile picture.
* User can change their personal information
* User can view help screen
* User can view about screen
* User can logout

**Optional Nice-to-have Stories**

* User can follow or Unfollow people
* User can share post publicly or in privately (friends only)
* User can categorize their post (Eg: Electronic, cosmetic, school, politics, furniture, etc)
* User can search posts based on category
* Users can pick company products and apply on the image in real time.

### 2. Screen Archetypes

*  Login Screen
   * User can login 
* Register Screen
   * User can create an account
* Home Timeline Screen
   * User can view their timeline
   * User can vote
   * User can scroll through the posts (infinite scroll)
* Post Creation Screen
   * User can create a post
* Profile Screen 
   * User can view their own posts 
* Profile Detail Screen
   * User can view profile details by clicking on profile picture.
* Settings Screen
   * User can change their personal information
   * User can view help screen
   * User can view about screen
   * User can logout

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Posts
* New Post
* Profile
  * Settings

Optional:
* AR Camera

**Flow Navigation** (Screen to Screen)
* Log-in -> Account creation if no log in is available
* Posts Timeline -> New Post
* Posts Timeline -> Profile
* Profile -> Settings. 
* Settings -> Toggle settings

## Wireframes
<img src="https://github.com/DocchiAndroid/Docchi/blob/main/digitalwireframedrawing.png" width=800><br>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/DocchiAndroid/Docchi/blob/main/Digital%20Wireframes.png" width=500 >

### [BONUS] Interactive Prototype
<img src="https://github.com/DocchiAndroid/Docchi/blob/main/DocchiDigitalWireframeWalkthrough.gif" height=500>

#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | user	   | Pointer to User| image author |
   | image         | File     | image that user posts |
   | description   | String   | image caption by author |
   | voteCount     | Number   | number of votes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |


#### Profile

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | user	   | Pointer to User| image author |
   | profileImage  | File     | Profile picture|
   | image         | File     | image that user posts |
   | description   | String   | image caption by author |
   | voteCount     | Number   | number of votes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |

#### Home

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | user	   | Pointer to User| image author |
   | profileImage  | File     | Profile picture|
   | image         | File     | image that user posts |
   | description   | String   | image caption by author |
   | voteCount    | Number   | number of votes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   
   ### Networking
#### List of network requests by screen
   - Login Screen
      - (POST) Username and password
      - (Read/GET) User authentication token
   - Signup Screen
      - (Create/Post) Create new user object
   - Home Feed Screen
      - (Read/GET) Query all recent posts
      - (Create/POST) Create a new vote on a post
      - (Delete) Delete existing vote
   - Create Post Screen
      - (Create/POST) Create a new post object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Read/Get) Query all posts where user is the author
      - (Update/PUT) Update username, password
      - (Update/PUT) Update user profile image
      - 


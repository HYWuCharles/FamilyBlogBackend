# Family Blog Document
### A demo for website backend develop using Spring Boot
Function implemented/to be implemented

* [x] Router process
* [x] Authentication code (Redis)
* [x] Web security configuration (Database based)
* [x] AOP log
* [x] MySQL database SQL generator
* [x] Elastic search
* [x] JWT
* [ ] Global exception handling
* [ ] Front-end design

## Routers
**All returned values are wrapped with** `CommonResult.class`

```java
/**
* Function: Find all users
* Authentication required: True
* PathVariable: Null
* RequestParam: Null
* Returns: List<User.class>
*/
@GetMapping
/user/listAll
```
```java
/**
* Function: Find single user by Id
* Authentication required: True
* PathVariable: id -> target user id
* RequestParam: Null
* Returns: User.class
*/
@GetMapping
/user/{id}
```
```java
/**
* Function: Generate verification code for registry and put in Redis
* Meanwhile, set session attribute "AuthCodeUUID" -> uuid
* uuid is generated for every single user, in order to get corresponding code
* Authentication code format in redis: 
* Key: uuid   -> Value: Auth: *generated code*
* This code will be deleted once it is verified, expired or re-generated
* Authentication required: False
* PathVariable: Null
* RequestParam: Null
* Returns: code
*/
@GetMapping
/user/getAuthCode
```
```java
/**
* Function: Upload user avatar (for registry and update profile)
* target upload destination is /static/avatar/yyyy/mm/dd/uuid.extension
* However, the path stored in database does not contains "/static"
* i.e. /avatar/yyyy/mm/dd/uuid.extension
* Authentication required: False
* PathVariable: Null
* RequestParam: avatar -> MultipartFile
* Returns: String -> upload path
*/
@PostMapping
/user/uploadAvatar
```
```java
/**
* Function: Find current logged user information
* Authentication required: True
* PathVariable: Null
* RequestParam: Null
* Returns: User.class
*/
@GetMapping
/user/current
```
```java
/**
* Function: Created a new user in database and log this user  
* Authentication required: False
* PathVariable: Null
* RequestParam: verifyCode -> the code got from /user/getAuthCode
* RequestBody: JSON format user object
* {"username": username ... }
* Returns: JWT details if on success
*/
@PostMapping
/user/registry
```
```java
/**
* Function: Delete a single user (If user role is USER, id has to be the same as current logged user)
* Authentication required: True
* PathVariable: id -> target user id
* RequestParam: Null
* Returns: deleted user id
*/
@GetMapping
/user/delete/{id}
```
```java
/**
* Function: Find current logged user information
* Authentication required: True
* PathVariable: id -> target user id
* RequestParam: Null
* RequestBody: JSON format user object
* {"username": username ... }
* Returns: updated user id
*/
@PostMapping
/user/update/{id}
```
```java
/**
* Function: Find current logged user blog visit history
* Authentication required: True
* PathVariable: userId -> target user id
* RequestParam: pageNum -> history page number
* pageSize -> history page size
* Returns: BlogVisitLog.class
*/
@GetMapping
/user/history/{userId}?pageNum=xx&pageSize=xx
```
```java
/**
* Function: Hide (not actually delete, this log can still be found in database, but just not shown on user end) a single blog visit log
* Authentication required: True
* PathVariable: blogVisitId -> target blog visit id
* RequestParam: Null
* Returns: String -> Message
*/
@GetMapping
/user/delete/{blogVisitId}
```
```java
/**
* Function: Redirect to /login.html
* Authentication required: False
* PathVariable: Null
* RequestParam: Null
* Returns: ModelAndView.class
*/
@GetMapping
/login
```
```java
/**
* Function: Log in
* Authentication required: False
* PathVariable: Null
* RequestParam: Null
* RequestBody: JSON format user object
* {"username": username ... }
* Returns: JWT details if on success
*/
@PostMapping
/login
```
```java
/**
* Function: Find all blogs
* Authentication required: True
* PathVariable: Null
* RequestParam: Null
* Returns: List<Blog.class>
*/
@GetMapping
/blog/listAll
```
```java
/**
* Function: Find all blogs belonging to a single user
* Authentication required: True
* PathVariable: id -> target user id
* RequestParam: Null
* Returns: List<Blog.class>
*/
@GetMapping
/blog/listAll/{id}
```
```java
/**
* Function: Find all blogs by page
* Authentication required: True
* PathVariable: Null
* RequestParam: pageNum -> page number
* pageSize -> page size
* Returns: List<Blog.class>
*/
@GetMapping
/blog/list?pageNum=xx&pageSize=xx
```
```java
/**
* Function: Find all blogs by page and by user
* Authentication required: True
* PathVariable: id -> target user id
* RequestParam: pageNum -> page number
* pageSize -> page size
* Returns: List<Blog.class>
*/
@GetMapping
/blog/list/{id}?pageNum=xx&pageSize=xx
```
```java
/**
* Function: Publish a new blog in database, draft will be deleted if publish from an existing draft
* Authentication required: True
* PathVariable: Null
* RequestParam: draftId -> if publish from an existing draft, not required
* RequestBody: JSON format Blog object
* {"content": content ...}
* Returns: Blog.class
*/
@PostMapping
/blog/publish?draftId=xx
```
```java
/**
* Function: Upload images for a blog
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* RequestBody: MultipartFile images
* Returns: List<String.class>
*/
@PostMapping
/blog/uploadImages/{blogId}
```
```java
/**
* Function: Find images for a blog
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* Returns: List<String.class>
*/
@GetMapping
/blog/images/{blogId}
```
```java
/**
* Function: Update blog in database
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* RequestBody: JSON format Blog object
* {"content": content ...}
* Returns: Blog.class
*/
@PostMapping
/blog/update/{blogId}
```
```java
/**
* Function: Delete blog in database
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* Returns: String -> Message 
*/
@GetMapping
/blog/delete/{blogId}
```
```java
/**
* Function: Find a single blog
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* Returns: Blog.class
*/
@GetMapping
/blog/{blogId}
```
```java
/**
* Function: Increase blog liked times by one
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* Returns: Blog.class
*/
@GetMapping
/blog/like/{blogId}
```
```java
/**
* Function: Decrease blog liked times by one
* Authentication required: True
* PathVariable: blogId -> target blog id
* RequestParam: Null
* Returns: Blog.class
*/
@GetMapping
/blog/unlike/{blogId}
```
```java
/**
* Function: Create a draft in Redis
* Authentication required: True
* PathVariable: Null
* RequestParam: draftId -> draft id, not required, update draft with id (draftId) in Redis if provided
* RequestBody: JSON format Blog object
* {"content": content ...}
* Returns: String -> A unique uuid, which is for get draft in Redis
*/
@PostMapping
/blog/uploadDraft?draftId=xx
```
```java
/**
* Function: Delete a draft in Redis
* Authentication required: True
* PathVariable: Null
* RequestParam: draftId -> draft id
* Returns: String -> Message
*/
@GetMapping
/blog/deleteDraft?draftId=xx
```
```java
/**
* Function: Get a draft in Redis
* Authentication required: True
* PathVariable: Null
* RequestParam: draftId -> draft id
* Returns: Blog.class
*/
@GetMapping
/blog/draft?draftId=xx
```
```java
/**
* Function: Get all drafts in Redis
* Authentication required: True
* PathVariable: Null
* RequestParam: Null
* Returns: List<Blog.class>
*/
@GetMapping
/blog/allDraft
```
```java
/**
* Function: Get most recent five blogs stored in database
* Authentication required: True
* PathVariable: Null
* RequestParam: Null
* Returns: List<Blog.class>
*/
@GetMapping
/blog/mostRecent
```
```java
/**
* Function: Import all documents from MySQL to ES service
* Authentication required: True (has to have `ADMIN` role)
* PathVariable: Null
* RequestParam: Null
* Returns: Integer -> Total amounts of blogs
*/
@GetMapping
/es/importAll
```
```java
/**
* Function: Search related documents from ES service
* Authentication required: True
* PathVariable: Null
* RequestParam: keyword -> search keyword (title and content)
* pageNum -> search page number
* pageSize -> search page size
* Returns: Integer -> List<Blog.class>
*/
@GetMapping
/es/search?keyword=xx&pageNum=xx&pageSize=xx
```

## AOP related
`StatusAOP` prints user logging on terminal and update user logging table in database every time a user successfully logged in

`ServiceAOP` will log information about each invoked method in `@Service` annotated classes

## Return value wrapping
`CommonResult` wrap every message returned to front end. 
It contains:
`code`: status code used in HTTP protocol
`message`: custom messages given to front end
`data`: JSON format data for front end processing

## Web Security related
In database, each user has a `ROLE` column, which identify the user's permissions. It has two levels, `USER` has normal permissions while `ADMIN` has every permission that `USER` has plus some backstage administration permissions (to be implemented).

Except login, registry, upload avatars, static resources, every method needs authentication. This is implemented by using `JWT`.

Each time a request comes in, `JwtAuthenticationFilter` will check if the request has header `Authorization`. If so, this class will try to extract username from JWT and try to invoke SpringBoot Security method `loadUserByUsername` and verify it. Requests from successfully verified users can be further processed while failed requests cannot.

JWT format:
Token header: Authorization
Token body: Bearer  token (There is a white space between Bearer and token)

## Elastic search related
Each time the server is started, admin users (administrator account which has role `ADMIN`) will have to visit url `/es/importAll` to import records from MySQL to ES. 

## Redis related
##### About drafts stored in Redis
When a draft is uploaded, things will happen as follows:
1. If the user has no other drafts, server will create a two-level cache

First level : `Set` -> `Key: username, Values: All drafts ids`
Second level: `Hashmap` -> `Key: draftId:username, Values: {"content":xxx, "title": xxx, ...}`
2.  When upload a new blog from a draft, related blog draft will be removed from Redis
3.  When delete a draft, related blog draft will be removed from Redis
4.  When a draft is expired, related blog draft will be removed from Redis, this operation is implemented by a Redis event listener
5.  Any of 2, 3, 4 operation is invoked, corresponding draft id will be removed from this user's first level cache
6.  If this user's set is empty, the set will be removed



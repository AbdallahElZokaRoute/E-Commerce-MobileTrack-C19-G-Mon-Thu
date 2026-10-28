# Cart Feature Spec

## Purpose

Manage the logged-in user's cart via Cart API v1: add a product, load the cart, update a line quantity, remove one item, and clear the cart. Persist nothing locally. The existing `AuthInterceptor` sends the `token` header. Expose an MVI Contract + ViewModel and wire `CartScreen`, plus the product-list add and cart-icon actions.

## Endpoints

Base URL: `https://ecommerce.routemisr.com/api/v1/`

| Method | Path | Auth |
|--------|------|------|
| POST | `/api/v1/cart` | `token` header |
| GET | `/api/v1/cart` | `token` header |
| PUT | `/api/v1/cart/{productId}` | `token` header |
| DELETE | `/api/v1/cart/{productId}` | `token` header |
| DELETE | `/api/v1/cart` | `token` header |

Coupon and Cart API v2 are out of scope.

### POST /api/v1/cart — Add product

Request body:

```json
{
  "productId": "6428eb43dc1175abc65ca0b3"
}
```

Response body (also used by PUT and DELETE item). `products[].product` is a product id string. GET uses a different payload, below.

```json
{
  "status": "success",
  "message": "Product added successfully to your cart",
  "numOfCartItems": 1,
  "cartId": "6adfad1fcfea3d0013fa6d3f",
  "data": {
    "_id": "6adfad1fcfea3d0013fa6d3f",
    "cartOwner": "6adf98b7cfea3d0013fa62c2",
    "products": [
      {
        "count": 1,
        "_id": "6adfad1fcfea3d0013fa6d40",
        "product": "6428eb43dc1175abc65ca0b3",
        "price": 149
      }
    ],
    "createdAt": "2026-10-26T19:42:23.218Z",
    "updatedAt": "2026-10-26T19:42:23.254Z",
    "__v": 0,
    "totalCartPrice": 149
  }
}
```

### PUT /api/v1/cart/{productId} — Update quantity

Sample: `PUT /api/v1/cart/6428eb43dc1175abc65ca0b3`

Request body. `count` is sent as a string, matching the original collection:

```json
{
  "count": "1"
}
```

Response body: same populated cart payload as GET. `products[].product` is an object with title and imageCover.

### GET /api/v1/cart — Get logged-in user cart

No body. `products[].product` is a populated product. The screen shows `title` and `imageCover`. Subcategory, category, and brand are present on the wire and ignored by the DTO (`ignoreUnknownKeys`).

```json
{
  "status": "success",
  "numOfCartItems": 1,
  "cartId": "6adfad1fcfea3d0013fa6d3f",
  "data": {
    "_id": "6adfad1fcfea3d0013fa6d3f",
    "cartOwner": "6adf98b7cfea3d0013fa62c2",
    "products": [
      {
        "count": 2,
        "_id": "6adfad1fcfea3d0013fa6d40",
        "product": {
          "subcategory": [
            {
              "_id": "6407f1bcb575d3b90bf95797",
              "name": "Women's Clothing",
              "slug": "women's-clothing",
              "category": "6439d58a0049ad0b52b9003f"
            }
          ],
          "_id": "6428eb43dc1175abc65ca0b3",
          "title": "Woman Shawl",
          "quantity": 220,
          "imageCover": "https://ecommerce.routemisr.com/Route-Academy-products/1680403266739-cover.jpeg",
          "category": {
            "_id": "6439d58a0049ad0b52b9003f",
            "name": "Women's Fashion",
            "slug": "women's-fashion",
            "image": "https://ecommerce.routemisr.com/Route-Academy-categories/1681511818071.jpeg"
          },
          "brand": {
            "_id": "64089bbe24b25627a253158b",
            "name": "DeFacto",
            "slug": "defacto",
            "image": "https://ecommerce.routemisr.com/Route-Academy-brands/1678285758109.png"
          },
          "ratingsAverage": 3.9,
          "id": "6428eb43dc1175abc65ca0b3"
        },
        "price": 149
      }
    ],
    "createdAt": "2026-10-26T19:42:23.218Z",
    "updatedAt": "2026-10-26T19:43:43.190Z",
    "__v": 0,
    "totalCartPrice": 298
  }
}
```

### DELETE /api/v1/cart/{productId} — Remove specific cart item

Sample: `DELETE /api/v1/cart/6adc9c37cfea3d0013f97460`

Path parameter is the product id. Response body: same populated cart payload as GET. `products[].product` is an object with title and imageCover.

### DELETE /api/v1/cart — Clear cart

No body. Response body:

```json
{
  "message": "success"
}
```

## Architecture layers

Clean Architecture (Domain → Data → Presentation), flattened classes, Retrofit + Kotlinx Serialization, Hilt constructor injection, `safeAPICall` from `data/api/SafeApiCall.kt`.

### File list

| Layer | File |
|-------|------|
| Domain entity | `domain/cart/entitiy/CartProductEntity.kt` |
| Domain entity | `domain/cart/entitiy/CartItemEntity.kt` |
| Domain entity | `domain/cart/entitiy/CartEntity.kt` |
| Domain entity | `domain/cart/entitiy/ClearCartEntity.kt` |
| Domain | `domain/cart/repository/CartRepository.kt` |
| Domain | `domain/cart/usecase/AddProductToCartUseCase.kt` |
| Domain | `domain/cart/usecase/GetCartUseCase.kt` |
| Domain | `domain/cart/usecase/UpdateCartProductQuantityUseCase.kt` |
| Domain | `domain/cart/usecase/RemoveCartItemUseCase.kt` |
| Domain | `domain/cart/usecase/ClearCartUseCase.kt` |
| Data DTO | `data/cart/model/AddToCartRequest.kt` |
| Data DTO | `data/cart/model/UpdateCartCountRequest.kt` |
| Data DTO | `data/cart/model/CartProductDto.kt` |
| Data DTO | `data/cart/model/CartDataDto.kt` |
| Data DTO | `data/cart/model/CartResponse.kt` |
| Data DTO | `data/cart/model/CartProductDetailsDto.kt` |
| Data DTO | `data/cart/model/GetCartProductDto.kt` |
| Data DTO | `data/cart/model/GetCartDataDto.kt` |
| Data DTO | `data/cart/model/GetCartResponse.kt` |
| Data DTO | `data/cart/model/ClearCartResponse.kt` |
| Data | `data/cart/dataSource/api/CartService.kt` |
| Data | `data/cart/dataSource/CartRemoteDataSourceImpl.kt` |
| Data | `data/cart/mapper/CartMapper.kt` |
| Data | `data/cart/repository/CartRepositoryImpl.kt` |
| DI | `di/cart/CartModule.kt` |
| Presentation | `peresentation/ui/screens/cart/CartContract.kt` |
| Presentation | `peresentation/ui/screens/cart/CartViewModel.kt` |
| Presentation | `peresentation/ui/screens/cart/CartScreen.kt` |

`CartRepository` and `CartRemoteDataSource` live in the same file, matching products.

## Use cases

- **AddProductToCartUseCase** `(productId: String) → Flow<ApiResult<CartEntity?>>`
- **GetCartUseCase** `() → Flow<ApiResult<CartEntity?>>`
- **UpdateCartProductQuantityUseCase** `(productId: String, count: String) → Flow<ApiResult<CartEntity?>>`
- **RemoveCartItemUseCase** `(productId: String) → Flow<ApiResult<CartEntity?>>`
- **ClearCartUseCase** `() → Flow<ApiResult<ClearCartEntity?>>`

Each use case delegates to `CartRepository`. Add maps the cart payload whose `product` field is a string id. Get, update quantity, and remove item map the payload whose `product` field is an object (`title`, `imageCover`, `quantity`, ids). Clear maps `{ "message" }` only.

## Contract (MVI)

### Actions

- `LoadCart`
- `UpdateQuantity(productId: String, count: Int)`
- `RemoveItem(productId: String)`
- `ClearCart`

### States

- `Idle`
- `Loading`
- `Error(message: String?)`
- `Success(cart: CartEntity)`

### Events

- `ShowToast(message: String?)`

## ViewModel behavior

1. On enter, `LoadCart` → `Loading` → collect `GetCartUseCase`.
2. Update quantity and remove item return the same populated cart as GET. `ApiResult.Success` writes `State.Success` from that payload, so title and image stay without a second GET.
3. `ClearCart` success → empty `CartEntity` + `Event.ShowToast` with `message`.
4. `ApiResult.Error` → `ClientException` / `ServerException` → `Event.ShowToast` (same pattern as ProductList). Quantity and remove errors keep the current cart on screen.

## Product list integration

- `AddedProductToCart` calls `AddProductToCartUseCase` and toasts `CartEntity.message`. The product list state is left unchanged.
- `ClickedOnCartIcon` emits `NavigateToCart`. `ProductListScreen` already navigates to `AppRoutes.Main.Cart`.
- The plus icon and the search-bar cart icon call those actions.

## Error handling

| Source | Handling |
|--------|----------|
| HTTP 2xx + body | Success |
| HTTP 500–599 | `ServerException` → toast |
| Other HTTP errors | `ClientException` (parsed `ErrorModel`) → toast |
| Network/unknown | `UnknownException` |

## Acceptance criteria

- [ ] POST serializes `productId` and maps the cart payload to `CartEntity` / `CartItemEntity` where `product` is a string id
- [ ] GET, PUT, and DELETE item map `products[].product` as an object onto `CartProductEntity` (`title`, `imageCover`)
- [ ] PUT sends `count` as a string
- [ ] DELETE item uses the product id path parameter
- [ ] DELETE cart maps `{ "message": "success" }` to `ClearCartEntity`
- [ ] Every call goes through `safeAPICall`
- [ ] CartViewModel exposes Contract states and events
- [ ] CartScreen loads the cart and shows title and cover image, updates quantity, removes a line (minus at count 1 removes), and clears the cart
- [ ] Product list plus adds the product and toasts the API message; cart icon opens the cart

## Out of scope

- Apply coupon
- Cart API v2
- Orders and checkout

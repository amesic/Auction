import { NgModule, Injectable } from "@angular/core";
import {
  Routes,
  RouterModule,
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { LandingPageComponent } from "./components/landingPage/landing-page/landing-page.component";
import { AllCategoriesComponent } from "./components/all-categories/all-categories.component";
import { SingleProductPageComponent } from "./components/singleProductPage/single-product-page/single-product-page.component";
import { ShopComponent } from "./components/shopProduct/shop/shop.component";
import { MyAccountComponent } from "./components/myAccount/my-account/my-account.component"
import { MyAccountProductsComponent } from "./components/myAccount/myAccountProducts/my-account-products/my-account-products.component"
import { MyAccountBidsComponent } from "./components/myAccount/my-account-bids/my-account-bids.component"
import { MyAccountWatchlistComponent } from "./components/myAccount/my-account-watchlist/my-account-watchlist.component"
import { LoginService } from "./services/login.service";
import { ErrorComponent } from "./components/error/error.component";

@Injectable()
export class LoginRegisterActivate implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.loginService.isUserLoggedIn()) {
      this.router.navigate(["/home"]);
    }
    return true;
  }
}

const routes: Routes = [
  { path: "", redirectTo: "/home", pathMatch: "full" },
  {
    path: "home",
    data: {
      breadcrumb: "HOME"
    },
    children: [
      {
        path: "login",
        component: LoginComponent,
        canActivate: [LoginRegisterActivate],
        data: {
          breadcrumb: "LOGIN"
        }
      },
      {
        path: "register",
        component: RegisterComponent,
        canActivate: [LoginRegisterActivate],
        data: {
          breadcrumb: "REGISTER"
        }
      },
      { path: "", component: LandingPageComponent },
      {
        path: "allCategories",
        component: AllCategoriesComponent,
        data: {
          breadcrumb: "ALL CATEGORIES"
        }
      }
    ]
  },
  {
    path: "shop",
    data: {
      breadcrumb: "SHOP"
    },
    children: [
      {
        path: "",
        component: ShopComponent,
        data: {
          breadcrumb: "ALL CATEGORIES"
        }
      },
      {
        path: "search/:searchValue/category/:categoryId/:subcategoryId",
        component: ShopComponent,
        data: {
          breadcrumb: "ALL CATEGORIES"
        }
      },
      {
        path: "category/:categoryId/:subcategoryId",
        component: ShopComponent,
        data: {
          breadcrumb: "ALL CATEGORIES"
        }
      },
      {
        path: "search/:searchValue",
        component: ShopComponent,
        data: {
          breadcrumb: "ALL CATEGORIES"
        }
      },
      {
        path: "product/:title/:idProduct",
        component: SingleProductPageComponent,
        data: {
          breadcrumb: "SINGLE PRODUCT"
        }
      }
    ]
  },
  {
    path: "my-account",
    data: {
      breadcrumb: "MY ACCOUNT"
    },
    children: [
      {
        path: "profile",
        component: MyAccountComponent,
        data: {
          breadcrumb: "PROFILE"
        }
      },
      {
        path: "products",
        component: MyAccountProductsComponent,
        data: {
          breadcrumb: "PRODUCTS"
        }
      },
      {
        path: "bids",
        component: MyAccountBidsComponent,
        data: {
          breadcrumb: "BIDS"
        }
      },
      {
        path: "watchlist",
        component: MyAccountWatchlistComponent,
        data: {
          breadcrumb: "WATCHLIST"
        }
      },
    ]
  },
  {
    path: "pageNotFound",
    component: ErrorComponent
  },
  {
    path: "**",
    redirectTo: "pageNotFound"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

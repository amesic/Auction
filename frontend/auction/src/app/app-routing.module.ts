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
import { ShopComponent } from "./components/shopProduct/shop/shop.component"
import { LoginService } from "./services/login.service";

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
        path: "product/:title/:idProduct",
        component: SingleProductPageComponent,
        data: {
          breadcrumb: "SINGLE PRODUCT"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

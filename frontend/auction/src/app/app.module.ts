import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { BasicAuthHtppInterceptorServiceService } from './services/basic-auth-htpp-interceptor-service.service';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { InputComponent } from "./components/reusable/input/input.component";
import { LoginComponent } from "./components/login/login.component";
import { FooterComponent } from "./components/footer/footer.component";
import { HeaderComponent } from "./components/header/header.component";
import { BreadcrumbsComponent } from "./components/reusable/breadcrumbs/breadcrumbs.component";
import { RegisterComponent } from "./components/register/register.component";
import { CategoriesComponent } from './components/landingPage/advertisementPage/categories/categories.component';
import { ShowHideDirective } from './directives/show-hide.directive';
import { ProductComponent } from './components/reusable/product/product.component';
import { AdvertisementComponent } from './components/landingPage/advertisementPage/advertisement/advertisement.component';
import { LandingPageComponent } from './components/landingPage/landing-page/landing-page.component';
import { SetProductsComponent } from './components/reusable/set-products/set-products.component';
import { AllCategoriesComponent } from './components/all-categories/all-categories.component';
import { PaginationComponent } from './components/landingPage/pagination/pagination.component';
import { SingleProductComponent } from './components/singleProductPage/single-product/single-product.component';
import { SingleProductPageComponent } from './components/singleProductPage/single-product-page/single-product-page.component';
import { BidsComponent } from './components/singleProductPage/bids/bids.component';
import { LoginRegisterActivate } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    InputComponent,
    LoginComponent,
    FooterComponent,
    HeaderComponent,
    BreadcrumbsComponent,
    RegisterComponent,
    CategoriesComponent,
    ShowHideDirective,
    ProductComponent,
    AdvertisementComponent,
    LandingPageComponent,
    SetProductsComponent,
    AllCategoriesComponent,
    PaginationComponent,
    SingleProductComponent,
    SingleProductPageComponent,
    BidsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule
  ],
  providers: [
    LoginRegisterActivate,
  {
      provide: HTTP_INTERCEPTORS,
      useClass: BasicAuthHtppInterceptorServiceService,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}

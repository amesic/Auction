import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { BasicAuthHtppInterceptorServiceService } from './services/basic-auth-htpp-interceptor-service.service';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { FormsModule } from '@angular/forms';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { Ng5SliderModule } from 'ng5-slider';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
import { ShopComponent } from './components/shopProduct/shop/shop.component';
import { FilterCategoriesComponent } from './components/shopProduct/filters/categories/filterCategories.component'; 
import { AllFiltersComponent } from './components/shopProduct/filters/all-filters/all-filters.component';
import { FilterComponent } from './components/shopProduct/filters/filter/filter.component';
import { ShopProductComponent } from './components/shopProduct/products/shop-product/shop-product.component';
import { ShopProductsComponent } from './components/shopProduct/products/shop-products/shop-products.component';
import { ErrorComponent } from './components/error/error.component';
import { ClickOutsideDirective } from './directives/click-outside.directive';
import { ChangeColorOnClickDirective } from './directives/change-color-on-click.directive';
import { FilterPriceComponent } from './components/shopProduct/filters/filter-price/filter-price.component';

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
    BidsComponent,
    ShopComponent,
    AllFiltersComponent,
    FilterCategoriesComponent,
    FilterComponent,
    ShopProductComponent,
    ShopProductsComponent,
    ErrorComponent,
    ClickOutsideDirective,
    ChangeColorOnClickDirective,
    FilterPriceComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
    FormsModule,
    NgxChartsModule,
    BrowserAnimationsModule,
    Ng5SliderModule
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

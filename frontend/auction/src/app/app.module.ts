import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { InputComponent } from "./components/input/input.component";
import { LoginComponent } from "./components/login/login.component";
import { FooterComponent } from "./components/footer/footer.component";
import { HeaderComponent } from "./components/header/header.component";
import { BelowHeaderComponent } from "./components/below-header/below-header.component";
import { RegisterComponent } from "./components/register/register.component";
import { CategoriesComponent } from './components/categories/categories.component';
import { ShowHideDirective } from './directives/show-hide.directive';
import { ProductComponent } from './components/product/product.component';
import { AdvertisementComponent } from './components/advertisement/advertisement.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { SetProductsComponent } from './components/set-products/set-products.component';
import { AllCategoriesComponent } from './components/all-categories/all-categories.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { SingleProductComponent } from './components/single-product/single-product.component';
import { SingleProductPageComponent } from './components/single-product-page/single-product-page.component';
import { BidsComponent } from './components/bids/bids.component';
import { LoginRegisterActivate } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    InputComponent,
    LoginComponent,
    FooterComponent,
    HeaderComponent,
    BelowHeaderComponent,
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
  providers: [LoginRegisterActivate],
  bootstrap: [AppComponent]
})
export class AppModule {}

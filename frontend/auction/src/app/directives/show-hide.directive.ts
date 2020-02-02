import {
  Directive,
  ElementRef,
  HostListener,
  HostBinding
} from "@angular/core";

@Directive({
  selector: "[appShowHide]"
})
export class ShowHideDirective {
  @HostBinding("class.show") isOpen = false;
  @HostBinding("class.hide") isHide = false;
  constructor() {}
  @HostListener("click")
  onClick() {
    this.isOpen = !this.isOpen;
    this.isHide = !this.isHide;
  }
}

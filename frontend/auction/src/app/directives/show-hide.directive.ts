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
  constructor(private el: ElementRef) {}
  @HostListener("click")
  onClick() {
    this.isOpen = !this.isOpen;
  }
}

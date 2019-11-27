import { Directive, HostBinding, HostListener, ElementRef } from '@angular/core';

@Directive({
  selector: '[appChangeColorOnClick]'
})
export class ChangeColorOnClickDirective {

  @HostBinding("class.change") isClicked;
  constructor(private el: ElementRef) {}
  @HostListener("click")
  onClick() {
    this.isClicked = true;
  }

}

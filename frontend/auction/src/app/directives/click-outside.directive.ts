import { Directive, ElementRef, EventEmitter, Output, HostListener, HostBinding } from '@angular/core';

@Directive({
  selector: '[appClickOutside]'
})
export class ClickOutsideDirective {
  constructor(private _elementRef : ElementRef) {
  }

  @Output()
  public appclickOutside = new EventEmitter();

  @HostListener('document:click', ['$event.target'])
  public onClick(targetElement) {
      const clickedInside = this._elementRef.nativeElement.contains(targetElement);
      if (!clickedInside) {
          this.appclickOutside.emit(null);
      }
  }

}

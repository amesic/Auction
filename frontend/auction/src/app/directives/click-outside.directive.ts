import { Directive, ElementRef, EventEmitter, Output, HostListener, HostBinding } from '@angular/core';

@Directive({
  selector: '[appClickOutside]'
})
export class ClickOutsideDirective {
  @HostBinding('class.show') isOpen = false;
  constructor(private _elementRef : ElementRef) {
  }

  @HostListener('document:click', ['$event']) onClick(e: Event) {
      this.isOpen = this._elementRef.nativeElement.contains(e.target) ? !this.isOpen : false;
  }

}

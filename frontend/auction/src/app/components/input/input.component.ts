import { Component, OnInit } from "@angular/core";
import { Input } from "@angular/core"; //koristimo komponentu Input
import { FormGroup, FormControl } from "@angular/forms";

@Component({
  selector: "app-input",
  templateUrl: "./input.component.html",
  styleUrls: ["./input.component.css"]
})
export class InputComponent implements OnInit {
  @Input() type: string; //roditelj (npr login) šalje informacije za type
  @Input() title: string; //roditelj (npr login) šalje informacije za title
  @Input() controlName: string; //roditelj (npr login) šalje informacije za ime inputa u određenoj formi
  @Input() parentForm: FormGroup;
  @Input() placeholder: String;
  @Input() errorMessage: String;

  constructor() {}

  ngOnInit() {}
}

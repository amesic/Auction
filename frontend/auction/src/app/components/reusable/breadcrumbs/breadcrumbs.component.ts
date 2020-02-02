import { Component, OnInit } from "@angular/core";
import { isNullOrUndefined } from "util";
import {
  ActivatedRoute,
  Router
} from "@angular/router";

@Component({
  selector: "app-breadcrumbs",
  templateUrl: "./breadcrumbs.component.html",
  styleUrls: ["./breadcrumbs.component.css"]
})
export class BreadcrumbsComponent implements OnInit {
  title1: string;
  title2: string;
  routerLinkTitle1;
  routerLinkTitle2;
  menu;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  static readonly ROUTE_DATA_BREADCRUMB = "breadcrumb";

  ngOnInit() {
    this.createBreadcrumbs(this.activatedRoute.root);
    if (this.breadcrumbs[0] != undefined) {
      this.title2 = this.breadcrumbs[0].label;
      this.routerLinkTitle2 = [this.breadcrumbs[0].url];
    }
    if (this.breadcrumbs[1] != undefined) {
      this.title1 = this.breadcrumbs[1].label;
      this.routerLinkTitle1 = [this.breadcrumbs[1].url];
    }
    this.breadcrumbs = <any>[];
  }
  breadcrumbs: any[] = <any>[];

  private createBreadcrumbs(route: ActivatedRoute, url: string = ""): any[] {
    const children: ActivatedRoute[] = route.children;
    if (children.length === 0) {
      return this.breadcrumbs;
    }
    for (const child of children) {
      const routeURL: string = child.snapshot.url
        .map(segment => segment.path)
        .join("/");
      if (routeURL !== "") {
        url += `/${routeURL}`;
      }
      const label =
        child.snapshot.data[BreadcrumbsComponent.ROUTE_DATA_BREADCRUMB];
      if (!isNullOrUndefined(label)) {
        this.breadcrumbs.push({ label, url });
      }
      this.createBreadcrumbs(child, url);
    }
  }
}

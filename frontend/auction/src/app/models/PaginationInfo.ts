import { Product } from "../models/Product";

export class PaginationInfo {
  pageSize;
  pageNumber;
  totalNumberOfItems;
  items: Product[];
}

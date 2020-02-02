export class CategoryInfo {
    id: number;
    name: string;
    numberOfProducts;
    subcategories: Array<CategoryInfo>;
}
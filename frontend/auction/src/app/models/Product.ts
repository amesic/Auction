import { Category } from './Category';
import { User } from './User';
import { ImageProduct } from './ImageProduct';

export class Product {
    id: number;
    title: string;
    datePublishing: Date;
    description: string;
    startDate: Date;
    endDate: Date;
    startPrice;
    category: Category;
    subcategory: Category;
    seller: User;
    images: Array<ImageProduct>;
    timeLeft;
  }
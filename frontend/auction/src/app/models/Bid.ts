import { Product } from './Product';
import { User } from './User';

export class Bid {
    id: number;
    date: Date;
    value: number;
    product: Product;
    user: User;
}
import { RestaurantMenu } from "./RestaurantMenu";

export interface Restaurant{
	restaurantId: number,
	restaurantName: string,
	rating: number,
	menuTypes: string[],
	imageUrl: string,
	location: string,
	menuItems?: RestaurantMenu[];
}

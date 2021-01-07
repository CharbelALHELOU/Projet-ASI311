export class Director {
    id: number;
    lastName: String;
    firstName: String;
    date: Date;

    public constructor(init?: Partial<Director>) {
        Object.assign(this, init);
    }
}
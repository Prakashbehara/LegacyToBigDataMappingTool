import { IFeed } from 'app/shared/model//feed.model';

export interface ITargetMapping {
    id?: number;
    fieldName?: string;
    fieldType?: string;
    fieldOrder?: number;
    feed?: IFeed;
}

export class TargetMapping implements ITargetMapping {
    constructor(
        public id?: number,
        public fieldName?: string,
        public fieldType?: string,
        public fieldOrder?: number,
        public feed?: IFeed
    ) {}
}

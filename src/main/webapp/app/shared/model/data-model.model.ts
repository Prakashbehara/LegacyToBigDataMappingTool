import { IDataModelMapping } from 'app/shared/model//data-model-mapping.model';

export const enum Domain {
    POLICY = 'POLICY',
    FINANCE = 'FINANCE',
    INGENIUM = 'INGENIUM',
    INVESTMENT = 'INVESTMENT',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface IDataModel {
    id?: number;
    entitiyName?: string;
    domain?: Domain;
    dataModelMappings?: IDataModelMapping[];
}

export class DataModel implements IDataModel {
    constructor(public id?: number, public entitiyName?: string, public domain?: Domain, public dataModelMappings?: IDataModelMapping[]) {}
}

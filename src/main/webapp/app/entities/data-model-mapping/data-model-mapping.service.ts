import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';

type EntityResponseType = HttpResponse<IDataModelMapping>;
type EntityArrayResponseType = HttpResponse<IDataModelMapping[]>;

@Injectable({ providedIn: 'root' })
export class DataModelMappingService {
    private resourceUrl = SERVER_API_URL + 'api/data-model-mappings';

    constructor(private http: HttpClient) {}

    create(dataModelMapping: IDataModelMapping): Observable<EntityResponseType> {
        return this.http.post<IDataModelMapping>(this.resourceUrl, dataModelMapping, { observe: 'response' });
    }

    update(dataModelMapping: IDataModelMapping): Observable<EntityResponseType> {
        return this.http.put<IDataModelMapping>(this.resourceUrl, dataModelMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDataModelMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataModelMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

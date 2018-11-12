import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';

type EntityResponseType = HttpResponse<ISourceDatabaseMapping>;
type EntityArrayResponseType = HttpResponse<ISourceDatabaseMapping[]>;

@Injectable({ providedIn: 'root' })
export class SourceDatabaseMappingService {
    private resourceUrl = SERVER_API_URL + 'api/source-database-mappings';

    constructor(private http: HttpClient) {}

    create(sourceDatabaseMapping: ISourceDatabaseMapping): Observable<EntityResponseType> {
        return this.http.post<ISourceDatabaseMapping>(this.resourceUrl, sourceDatabaseMapping, { observe: 'response' });
    }

    update(sourceDatabaseMapping: ISourceDatabaseMapping): Observable<EntityResponseType> {
        return this.http.put<ISourceDatabaseMapping>(this.resourceUrl, sourceDatabaseMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISourceDatabaseMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceDatabaseMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

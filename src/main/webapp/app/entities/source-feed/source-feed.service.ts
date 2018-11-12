import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceFeed } from 'app/shared/model/source-feed.model';

type EntityResponseType = HttpResponse<ISourceFeed>;
type EntityArrayResponseType = HttpResponse<ISourceFeed[]>;

@Injectable({ providedIn: 'root' })
export class SourceFeedService {
    private resourceUrl = SERVER_API_URL + 'api/source-feeds';

    constructor(private http: HttpClient) {}

    create(sourceFeed: ISourceFeed): Observable<EntityResponseType> {
        return this.http.post<ISourceFeed>(this.resourceUrl, sourceFeed, { observe: 'response' });
    }

    update(sourceFeed: ISourceFeed): Observable<EntityResponseType> {
        return this.http.put<ISourceFeed>(this.resourceUrl, sourceFeed, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISourceFeed>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceFeed[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

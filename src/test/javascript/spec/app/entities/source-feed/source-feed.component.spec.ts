/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedComponent } from 'app/entities/source-feed/source-feed.component';
import { SourceFeedService } from 'app/entities/source-feed/source-feed.service';
import { SourceFeed } from 'app/shared/model/source-feed.model';

describe('Component Tests', () => {
    describe('SourceFeed Management Component', () => {
        let comp: SourceFeedComponent;
        let fixture: ComponentFixture<SourceFeedComponent>;
        let service: SourceFeedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedComponent],
                providers: []
            })
                .overrideTemplate(SourceFeedComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceFeedComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceFeedService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SourceFeed(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sourceFeeds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

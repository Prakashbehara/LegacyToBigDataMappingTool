/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedUpdateComponent } from 'app/entities/source-feed/source-feed-update.component';
import { SourceFeedService } from 'app/entities/source-feed/source-feed.service';
import { SourceFeed } from 'app/shared/model/source-feed.model';

describe('Component Tests', () => {
    describe('SourceFeed Management Update Component', () => {
        let comp: SourceFeedUpdateComponent;
        let fixture: ComponentFixture<SourceFeedUpdateComponent>;
        let service: SourceFeedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedUpdateComponent]
            })
                .overrideTemplate(SourceFeedUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceFeedUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceFeedService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceFeed(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceFeed = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceFeed();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceFeed = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
